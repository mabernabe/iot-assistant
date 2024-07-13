package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

@Service
public class TransductorSetUpInterfaceService implements TransductorInterfaceVisitor {
	
	@Autowired
	private MQTTTransductorsController mqttTransductorsController;
	
	@Autowired
	private TransductorsService transductorsService;
	

	public void setUp(TransductorInterface transductorInterface) {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
		try {
			this.mqttTransductorsController.subscribe(sensorMqttInterface);
		} catch (MqttException e) {
			this.transductorsService.setActive(sensorMqttInterface.getTopic(), false);
		}	
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		try {
			this.mqttTransductorsController.subscribe(actuatorMqttInterface);
		} catch (MqttException e) {
			this.transductorsService.setActive(actuatorMqttInterface.getTopic(), false);
		}
		
	}


}
