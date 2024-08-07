package com.iotassistant.services;

import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.ActuatorInterfaceVisitor;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.mqtt.MqttActuatorInterface;
import com.iotassistant.mqtt.MqttDevicesController;

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
	public void visit(MqttActuatorInterface actuatorMqttInterface) {
		MqttDevicesController.getInstance().setActuatorValue(actuatorMqttInterface, propertyActuated, value);	
	}


	

}
