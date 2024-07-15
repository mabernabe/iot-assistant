package com.iotassistant.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.ActuatorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;

@Service
public class ActuatorSetValueService implements ActuatorInterfaceVisitor{
	
	@Autowired
	private MQTTTransductorsController mqttTransductorsController;

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		// TODO Auto-generated method stub
		
	}


	

}
