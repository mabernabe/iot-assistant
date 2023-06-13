package com.iotassistant.models.transductormqttinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.transductor.SensorMeasure;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.SensorInterface;
import com.iotassistant.models.transductor.SensorMeasureValueEvent;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalPropertyMeasured;
import com.iotassistant.utils.JSONParser;

@Entity
@DiscriminatorValue("sensorMQTTInterface")
public class SensorMqttInterface extends SensorInterface implements TransductorMqttInterfaceObserver{
	
	@OneToOne(cascade=CascadeType.ALL)
	private MqttSensorTopic topic;
	
	@Transient
	private TransductorMqttInterface transductorMqttInterface;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@Enumerated(EnumType.STRING)
	private List<PropertyMeasuredEnum> propertiesMeasured; 
	
	@Transient
	private MqttSensorMeasuresMessage lastMeasures;
	
	@Transient
    private Set<SensorMeasureObserver> measureObservers;
	
	
	
	public SensorMqttInterface() {
		super();
		lastMeasures = null;
		measureObservers = new HashSet<SensorMeasureObserver>();
		this.transductorMqttInterface = new TransductorMqttInterface(this);
	}

	public SensorMqttInterface(String topic, List<PropertyMeasuredEnum> propertiesMeasured) {
		this();
		this.topic = new MqttSensorTopic(topic);
		this.propertiesMeasured = propertiesMeasured;		
	}
	
	
	@Override
	public List<SensorMeasure> getMeasures() throws TransductorInterfaceException {
		if (transductorMqttInterface.isInterfaceError()) {
			throw new TransductorInterfaceException("MQTT Interface error");
		}
		if (lastMeasures != null) {
			return lastMeasures.getMeasures();
		}
		//First time and  no message retained is in broker
		else  {
			return new ArrayList<SensorMeasure>();
		}    
	}
	
	@Override
	public boolean messageArrived(String topic, String message)  {
			MqttSensorMeasuresMessage lastInBrokerMeasures;
			try {
				lastInBrokerMeasures = new JSONParser().parseJsonBodyAs(MqttSensorMeasuresMessage.class, message);
				lastMeasures = lastInBrokerMeasures;
				notifyMeasureObservers(lastMeasures);
				return false;
			} catch (IOException | IllegalArgumentException e) { //Error parsing the message arrived
				return true;
			} catch (Exception e) { // Error notifying the observers
				e.printStackTrace();
				return false;
			}		
	}
	
	private void notifyMeasureObservers(MqttSensorMeasuresMessage lastMeasures) throws Exception {
		for(SensorMeasureObserver observer: measureObservers) {		
			SensorMeasure measure = getMeasureObserverIsInterested(lastMeasures.getMeasures(), observer);
			if (measure!= null) {
				notifyMeasureToObserver(measure, observer);
			}
		}		
	}
	
	private SensorMeasure getMeasureObserverIsInterested(List<SensorMeasure> measures, SensorMeasureObserver observer) throws Exception {	
		SensorMeasure measureInterested = null;
		PropertyMeasuredEnum propertyObserved = observer.getPropertyObserved();
		for (SensorMeasure measure : measures) {
			if (measure.getPropertyMeasured().equals(propertyObserved) && isThresholdReached(propertyObserved, observer.getValueThresholdObserved(), measure.getValue(), observer.getAnalogThresholdOperator())) {
				measureInterested = measure;
			}
		}
		return measureInterested;
	}
	
	private void notifyMeasureToObserver(SensorMeasure measure, SensorMeasureObserver observer) {
		SensorMeasureValueEvent sensorEvent = new SensorMeasureValueEvent(measure.getValue(), measure.getDate());
		observer.notify(sensorEvent);
	}


	private boolean isThresholdReached(PropertyMeasuredEnum propertyObserved, String valueThreshold, String value, AnalogThresholdOperatorEnum analogThresholdOperatorEnum) throws Exception{
		boolean isThresholdReached = false;
		if (propertyObserved.isDigital()) {
			boolean digitalThreshold = DigitalPropertyMeasured.getDigitalValueFromString(valueThreshold);
			boolean digitalValue = DigitalPropertyMeasured.getDigitalValueFromString(value);
			if (digitalThreshold == digitalValue) {
				isThresholdReached = true;
			}
		}
		else {
			Integer analogThreshold = Integer.valueOf(valueThreshold);
			Float analogValue = Float.valueOf(value);
			isThresholdReached = analogThresholdOperatorEnum.isThresholdReached(analogThreshold, analogValue);
		}
		return isThresholdReached;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getProperties() {
		return (List<Property>) (List<? extends Property>) propertiesMeasured;
	}


	@Override
	public void registerMeasureObserver(SensorMeasureObserver observer) {
		measureObservers.add(observer);
	}


	@Override
	public void unRegisterMeasureObserver(SensorMeasureObserver observer) {		
		measureObservers.remove(observer);
	}
	
	
	
	public void registerOfflineObserver(SensorMeasureObserver observer) {
		measureObservers.add(observer);
	}


	
	public void unRegisterOfflineObserver(SensorMeasureObserver observer) {		
		measureObservers.remove(observer);
	}
	

	@Override
	public void setUp() throws TransductorInterfaceException  {
		this.transductorMqttInterface.setUp(this.topic);
		
		
	}

	@Override
	public void setDown() throws TransductorInterfaceException {
		measureObservers.clear();
		this.transductorMqttInterface.setDown();
	}

	@Override
	public void lwtMessageArrived(String message) {
		
		
	}

	
}
