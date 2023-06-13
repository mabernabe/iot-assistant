package com.iotassistant.controllers.dtos.transductor;


import java.util.HashMap;

import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.ActuatorInterface;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductorpininterface.ActuatorPinInterface;

public class NewPinInterfaceActuatorDTO extends NewTransductorRequestDTO{
	
	private HashMap<PropertyActuatedEnum, PinId  > pinsConfiguration;

	public HashMap<PropertyActuatedEnum, PinId> getPinsConfiguration() {
		return pinsConfiguration;
	}

	public void setPinsConfiguration(HashMap<PropertyActuatedEnum, PinId> pinsConfiguration) {
		this.pinsConfiguration = pinsConfiguration;
	}

	public Actuator getActuator() {
		ActuatorInterface actuatorPinInterface = new ActuatorPinInterface(pinsConfiguration);
		return new Actuator(super.getName(), super.getDescription(), actuatorPinInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));
	}

	
	
}
