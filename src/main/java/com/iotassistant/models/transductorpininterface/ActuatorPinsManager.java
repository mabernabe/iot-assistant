package com.iotassistant.models.transductorpininterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.PlatformInterfacesFactory;
import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.pininterface.PlatformPinInterface;
import com.iotassistant.models.transductor.ActuatorValue;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductor.propertyactuated.digital.DigitalPropertyActuated;

public class ActuatorPinsManager{
	
	protected PlatformPinInterface platformPinInterface;
	
	private HashMap< PropertyActuatedEnum, PinId> analogPins = new HashMap<PropertyActuatedEnum, PinId>();

	private HashMap< PropertyActuatedEnum, PinId> digitalPins = new HashMap<PropertyActuatedEnum, PinId>();


	public ActuatorPinsManager(ActuatorPinsConfiguration actuatorPinsConfiguration) throws PinInterfaceException {
		platformPinInterface = PlatformInterfacesFactory.getInstance().getPlatformPinInterface();
		setUpPins(actuatorPinsConfiguration);
	}
	
	private void setUpPins(ActuatorPinsConfiguration actuatorPinsConfiguration) throws PinInterfaceException {
		setUpAnalogsPins(actuatorPinsConfiguration);
		setUpDigitalPins(actuatorPinsConfiguration);
	}
	
	private void setUpAnalogsPins(ActuatorPinsConfiguration actuatorPinsConfiguration) throws PinInterfaceException {
		for (PinId pinId :  actuatorPinsConfiguration.getPinIds(PinCapability.ANALOG)) {
			platformPinInterface.setUpAnalogOutputPin(pinId);
			analogPins.put(actuatorPinsConfiguration.getPropertyActuated(pinId), pinId);
		}
	}
	
	private void setUpDigitalPins(ActuatorPinsConfiguration actuatorPinsConfiguration) throws PinInterfaceException {
		for (PinId pinId :  actuatorPinsConfiguration.getPinIds(PinCapability.DIGITAL)) {
			platformPinInterface.setUpDigitalOutputPin(pinId);
			digitalPins.put(actuatorPinsConfiguration.getPropertyActuated(pinId), pinId);
		}
		
	}
	
	public void setValue(PropertyActuatedEnum propertyActuated, String value) throws PinInterfaceException {
		if (analogPins.containsKey(propertyActuated)) {
			setUpAnalogPinValue(analogPins.get(propertyActuated), value);
		}
		else if (digitalPins.containsKey(propertyActuated)) {
			setUpDigitalPinValue(digitalPins.get(propertyActuated), value);
		}
		else {assert(true);}
	}
	
	private void setUpDigitalPinValue(PinId pinId, String value) throws PinInterfaceException {
		Boolean state = DigitalPropertyActuated.getDigitalValueFromString(value);
		assert(state != null);
		platformPinInterface.setDigitalOutputPinState(state, pinId);
		
	}

	private void setUpAnalogPinValue(PinId pinId, String value) throws PinInterfaceException {
		platformPinInterface.setAnalogOutputPinValue(value, pinId);	
	}

	public List <ActuatorValue> getValues() throws PinInterfaceException {
		List<ActuatorValue> values = new ArrayList<ActuatorValue>();
		for (PropertyActuatedEnum propertyActuated : analogPins.keySet()) {
			String value = platformPinInterface.getAnalogOutputPinValue(analogPins.get(propertyActuated));
			values.add(new ActuatorValue(value, propertyActuated));
		}
		for (PropertyActuatedEnum propertyActuated : digitalPins.keySet()) {
			boolean state = platformPinInterface.getDigitalOutputPinState(digitalPins.get(propertyActuated));
			String value = DigitalPropertyActuated.getDigitalValueString(state);
			values.add(new ActuatorValue(value, propertyActuated));
		}
		return values;
	}

	public void setDownPins() throws PinInterfaceException {
		for (PinId pinId : analogPins.values()) {
			platformPinInterface.setDownAnalogInputPin(pinId);
		}
		for (PinId pinId : digitalPins.values()) {
			platformPinInterface.setDownAnalogInputPin(pinId);
		}
		
	}

}
