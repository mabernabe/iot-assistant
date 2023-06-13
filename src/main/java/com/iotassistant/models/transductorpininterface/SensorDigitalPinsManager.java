package com.iotassistant.models.transductorpininterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.transductor.SensorMeasure;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalPropertyMeasured;

public class SensorDigitalPinsManager extends SensorPinsManager{

	private HashMap< PropertyMeasuredEnum, PinId> digitalPins = new HashMap<PropertyMeasuredEnum, PinId>();
	
	private HashMap<SensorMeasureObserver, SensorMeasureDigitalPinListener> sensorMeasureObservers = new HashMap<>();
	


	public SensorDigitalPinsManager(SensorPinsConfiguration sensorPinsConfiguration) throws PinInterfaceException {
		super();
		setUpPins(sensorPinsConfiguration);
	}

	private void setUpPins(SensorPinsConfiguration sensorPinsConfiguration) throws PinInterfaceException {
		for (PinId pinId :  sensorPinsConfiguration.getPins(PinCapability.DIGITAL)) {
			platformPinInterface.setUpDigitaInputPin(pinId);
			digitalPins.put(sensorPinsConfiguration.getPropertyMeasured(pinId), pinId);
		}
	}

	protected List<SensorMeasure> getMeasures() throws PinInterfaceException {
		List<SensorMeasure> measures = new ArrayList<SensorMeasure>();
		for (PropertyMeasuredEnum propertyMeasured : digitalPins.keySet()) {
			boolean digitalValue = platformPinInterface.isDigitalInputPinHigh(digitalPins.get(propertyMeasured));
			measures.add(new SensorMeasure(DigitalPropertyMeasured.getDigitalValueString(digitalValue), propertyMeasured));
		}
		return measures;
	}

	protected void registerMeasureObserver(SensorMeasureObserver observer, boolean digitalValueThreshold) throws PinInterfaceException {
		PropertyMeasuredEnum propertyMeasured = observer.getPropertyObserved();
		PinId pinId = digitalPins.get(propertyMeasured);
		SensorMeasureDigitalPinListener sensorMeasureDigitalPinListener = new SensorMeasureDigitalPinListener(observer, digitalValueThreshold , pinId);		
		platformPinInterface.addListener(sensorMeasureDigitalPinListener);
		sensorMeasureObservers.put(observer, sensorMeasureDigitalPinListener);

	}


	protected void removeMeasureObserver(SensorMeasureObserver observer) throws PinInterfaceException {
		SensorMeasureDigitalPinListener digitalPinListener = sensorMeasureObservers.get(observer);
		platformPinInterface.removeListener( digitalPinListener);
		sensorMeasureObservers.remove(observer);
	}

	protected void setDownPins() throws PinInterfaceException {
		for (PinId pinId : digitalPins.values()) {
			platformPinInterface.setDownDigitalInputPin(pinId);
		}
	}
	
}
