package com.iotassistant.models.transductormqttinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.iotassistant.models.transductor.ActuatorInterface;
import com.iotassistant.models.transductor.ActuatorValue;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.utils.JSONParser;

@Entity
@DiscriminatorValue("actuatorMQTTInterface")
public class ActuatorMqttInterface extends ActuatorInterface implements TransductorMqttInterfaceObserver{
	
	@OneToOne(cascade=CascadeType.ALL)
	private MqttActuatorTopic topic;
	
	@Transient
	private TransductorMqttInterface transductorMqttInterface;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@Enumerated(EnumType.STRING)
	private List<PropertyActuatedEnum> propertiesActuated; 
	
	@Transient
	private MqttActuatorValuesMessage values;

	public ActuatorMqttInterface() {
		super();
		values = null;
		this.transductorMqttInterface = new TransductorMqttInterface(this);
	}

	public ActuatorMqttInterface(String topic, List<PropertyActuatedEnum> propertiesActuated) {
		this();
		this.topic = new MqttActuatorTopic(topic);
		this.propertiesActuated = propertiesActuated;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Property> getProperties() {
		return (List<Property>) (List<? extends Property>) propertiesActuated;
	}

	@Override
	public void setValue(PropertyActuatedEnum propertyActuated, String value) throws TransductorInterfaceException {
		if (transductorMqttInterface.isInterfaceError()) {
			throw new TransductorInterfaceException("MQTT Interface error");
		}
		MqttActuatorSetValueMessage message = new MqttActuatorSetValueMessage(propertyActuated, value );
		try {
			transductorMqttInterface.sendMessage(topic.getSentStateTopic(), message);
		} catch (Exception e) {
			throw new TransductorInterfaceException("Cant sent the new state to Actuator");
		} 		
	}

	@Override
	public List<ActuatorValue> getValues() throws TransductorInterfaceException {
		if (transductorMqttInterface.isInterfaceError()) {
			throw new TransductorInterfaceException("MQTT Interface error");
		}
		if (values != null) {
			return values.getValues();
		}
		//First time and  no message retained is in broker
		else  {
			return new ArrayList<ActuatorValue>();
		}    
	}

	@Override
	public boolean messageArrived(String topic, String message) {
		MqttActuatorValuesMessage lastInBrokerValues;
		try {
			lastInBrokerValues = new JSONParser().parseJsonBodyAs(MqttActuatorValuesMessage.class, message);
			values = lastInBrokerValues;
			return false;
		} catch (IOException e) { //Error parsing the message arrived
			return true;
		} catch (Exception e) { // Error notifying the observers
			e.printStackTrace();
			return false;
		}		
	}


	@Override
	public void setUp() throws TransductorInterfaceException {
		this.transductorMqttInterface.setUp(this.topic);
		
	}

	@Override
	public void setDown() throws TransductorInterfaceException {
		this.transductorMqttInterface.setDown();
		
	}

	@Override
	public void lwtMessageArrived(String message) {
		// TODO Auto-generated method stub
		
	}

	

}
