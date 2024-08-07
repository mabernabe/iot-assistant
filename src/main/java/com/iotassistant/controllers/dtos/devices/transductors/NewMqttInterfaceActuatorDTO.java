package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.List;

import com.iotassistant.controllers.dtos.devices.NewDeviceRequestDTO;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.ActuatorInterface;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.mqtt.MqttActuatorInterface;

public class NewMqttInterfaceActuatorDTO extends NewDeviceRequestDTO{
	
	private List<PropertyActuatedEnum> propertiesActuated;

	public List<PropertyActuatedEnum> getPropertiesActuated() {
		return propertiesActuated;
	}

	public void setPropertiesActuated(List<PropertyActuatedEnum> propertiesActuated) {
		this.propertiesActuated = propertiesActuated;
	}

	public Actuator getActuator() {
		ActuatorInterface actuatorMqttInterface = new MqttActuatorInterface(super.getName());
		return new Actuator(super.getName(), super.getDescription(), propertiesActuated, actuatorMqttInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));

	}

}
