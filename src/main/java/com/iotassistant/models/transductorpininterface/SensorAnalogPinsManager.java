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

public class SensorAnalogPinsManager extends SensorPinsManager {

	private HashMap< PropertyMeasuredEnum, PinId> analogPins = new HashMap<PropertyMeasuredEnum, PinId>();
	
	public SensorAnalogPinsManager(SensorPinsConfiguration sensorPinsConfiguration) throws PinInterfaceException {
		super();
		for (PinId pinId : sensorPinsConfiguration.getPins(PinCapability.ANALOG)) {			
			analogPins.put(sensorPinsConfiguration.getPropertyMeasured(pinId), pinId);
		}
		setUpPins();
	}

	private void setUpPins() throws PinInterfaceException {
		for (PinId pinId : analogPins.values()) {
			platformPinInterface.setUpAnalogInputPin(pinId);
		}
	}


	protected List<SensorMeasure> getMeasures() throws PinInterfaceException   {
		List<SensorMeasure> measures = new ArrayList<SensorMeasure>();
		for (PropertyMeasuredEnum propertyMeasured : analogPins.keySet()) {
			String analogValue;
			analogValue = platformPinInterface.getAnalogInputValue(analogPins.get(propertyMeasured));
			measures.add(new SensorMeasure(analogValue, propertyMeasured));
		}
		return measures;
	}

	public void registerMeasureObserver(SensorMeasureObserver observer, String valueThreshold) {	
	}
	
	protected void setDownPins() throws PinInterfaceException {
		for (PinId pinId : analogPins.values()) {
			platformPinInterface.setDownAnalogInputPin(pinId);
		}
	}


}
