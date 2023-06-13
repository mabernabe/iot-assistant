package com.iotassistant.models.transductorpininterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PostRemove;
import javax.persistence.Transient;

import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.transductor.SensorMeasure;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.SensorInterface;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalPropertyMeasured;

@Entity
@DiscriminatorValue("sensorPINInterface")
public class SensorPinInterface extends SensorInterface{

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	protected SensorPinsConfiguration sensorPinsConfiguration ;

	@Transient
	private SensorAnalogPinsManager analogPinsManager;

	@Transient
	private SensorDigitalPinsManager digitalPinsManager;

	@Transient
	private final HashMap<SensorMeasureObserver, PropertyMeasuredEnum > measureObservers = new HashMap<>();



	public SensorPinInterface() {
		super();
	}

	public SensorPinInterface(HashMap<PropertyMeasuredEnum, PinId> sensorPinsConfiguration) {
		this.sensorPinsConfiguration = new SensorPinsConfiguration(sensorPinsConfiguration);
	}


	private void initializePinsManagers() throws PinInterfaceException {
		analogPinsManager = new SensorAnalogPinsManager(this.sensorPinsConfiguration);
		digitalPinsManager = new SensorDigitalPinsManager(this.sensorPinsConfiguration);	
	}

	@PostRemove
	private void terminatePinsManagers() throws PinInterfaceException {
		analogPinsManager.setDownPins();
		digitalPinsManager.setDownPins();
	}


	@Override
	public List<Property> getProperties() {
		return sensorPinsConfiguration.getProperties();
	}

	@Override
	public List<SensorMeasure> getMeasures() throws TransductorInterfaceException {
		List<SensorMeasure> measures = new ArrayList<SensorMeasure>();		
		try {
			measures.addAll(digitalPinsManager.getMeasures());
			measures.addAll(analogPinsManager.getMeasures());
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}
		return measures;
	}

	@Override
	public void registerMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException {
		PropertyMeasuredEnum propertyMeasured = observer.getPropertyObserved();
		if (propertyMeasured.isDigital()) {
			Boolean digitalThreshold = DigitalPropertyMeasured.getDigitalValueFromString(observer.getValueThresholdObserved());
			assert(digitalThreshold != null);
			try {
				digitalPinsManager.registerMeasureObserver(observer, digitalThreshold);
			} catch (PinInterfaceException e) {
				throw new TransductorInterfaceException(e.getErrorMessage());
			}
		}
		else {
			String analogThreshold = observer.getValueThresholdObserved();
			analogPinsManager.registerMeasureObserver(observer, analogThreshold);
		}
		measureObservers.put(observer, propertyMeasured );

	}


	@Override
	public void unRegisterMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException {
		if (measureObservers.get(observer).isDigital()) {
			try {
				digitalPinsManager.removeMeasureObserver(observer);
			} catch (PinInterfaceException e) {
				throw new TransductorInterfaceException(e.getErrorMessage());
			}
		}
	}

	@Override
	public void setUp() throws TransductorInterfaceException  {
		try {
			this.initializePinsManagers();
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}	
	}

	@Override
	public void setDown() throws TransductorInterfaceException {
		try {
			this.terminatePinsManagers();
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}
		
	}



}
