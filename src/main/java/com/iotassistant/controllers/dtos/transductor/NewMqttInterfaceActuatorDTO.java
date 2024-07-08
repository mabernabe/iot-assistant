package com.iotassistant.controllers.dtos.transductor;

import java.util.List;

import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.ActuatorInterface;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;

public class NewMqttInterfaceActuatorDTO extends NewTransductorRequestDTO{
	
	private List<PropertyActuatedEnum> propertiesActuated;

	public List<PropertyActuatedEnum> getPropertiesActuated() {
		return propertiesActuated;
	}

	public void setPropertiesActuated(List<PropertyActuatedEnum> propertiesActuated) {
		this.propertiesActuated = propertiesActuated;
	}

	public Actuator getActuator() {
		ActuatorInterface actuatorMqttInterface = new ActuatorMqttInterface(super.getName());
		return new Actuator(super.getName(), super.getDescription(), propertiesActuated, actuatorMqttInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));

	}

}
