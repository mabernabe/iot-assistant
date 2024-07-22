package com.iotassistant.services;

import com.iotassistant.models.devices.Actuator;
import com.iotassistant.models.devices.ActuatorInterfaceVisitor;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.MqttTransductorsController;

class ActuatorSetValueService implements ActuatorInterfaceVisitor{
	
	private Actuator actuator;
	
	private PropertyActuatedEnum propertyActuated;
	
	private String value;
	
	ActuatorSetValueService(Actuator actuator, PropertyActuatedEnum propertyActuated, String value) {
		super();
		this.propertyActuated = propertyActuated;
		this.actuator = actuator;
		this.value = value;
	}
	
	void setValue() {
		actuator.getInterface().accept(this);
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		MqttTransductorsController.getInstance().setActuatorValue(actuatorMqttInterface, propertyActuated, value);	
	}


	

}
