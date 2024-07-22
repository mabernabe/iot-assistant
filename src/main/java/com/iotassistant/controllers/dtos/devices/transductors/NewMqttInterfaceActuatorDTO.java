package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.List;

import com.iotassistant.models.devices.Actuator;
import com.iotassistant.models.devices.ActuatorInterface;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.mqtt.ActuatorMqttInterface;

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
