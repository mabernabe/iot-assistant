package com.iotassistant.services;

import com.iotassistant.controllers.MqttTransductorsController;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.ActuatorInterfaceVisitor;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;

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
