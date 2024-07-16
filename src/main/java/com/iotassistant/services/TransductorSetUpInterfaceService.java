package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;


public class TransductorSetUpInterfaceService implements TransductorInterfaceVisitor {
	
	private DevicesService devicesService;
	
	private MQTTTransductorsController mqttTransductorsController;
	

	public TransductorSetUpInterfaceService() {
		super();
		this.devicesService = DevicesService.getInstance();
		this.mqttTransductorsController = MQTTTransductorsController.getInstance();
	}

	public void setUp(TransductorInterface transductorInterface) {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
		try {
			mqttTransductorsController.subscribe(sensorMqttInterface);
		} catch (MqttException e) {
			this.devicesService.setActive(sensorMqttInterface.getTopic(), false);
		}	
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		try {
			mqttTransductorsController.subscribe(actuatorMqttInterface);
		} catch (MqttException e) {
			this.devicesService.setActive(actuatorMqttInterface.getTopic(), false);
		}
		
	}


}
