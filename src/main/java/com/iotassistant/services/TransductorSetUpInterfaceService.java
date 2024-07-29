package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iotassistant.models.devices.TransductorInterface;
import com.iotassistant.models.devices.TransductorInterfaceVisitor;
import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.MqttInterface;
import com.iotassistant.mqtt.MqttTransductorsController;
import com.iotassistant.mqtt.SensorMqttInterface;


class TransductorSetUpInterfaceService implements TransductorInterfaceVisitor {

	Logger logger = LoggerFactory.getLogger(TransductorSetUpInterfaceService.class);
	
	public TransductorSetUpInterfaceService() {
		super();
	}

	public void setUp(TransductorInterface transductorInterface) {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
			this.setUpMqttInterface(sensorMqttInterface);
	}
	
	private void setUpMqttInterface(MqttInterface mqttInterface) {
		try {
			MqttTransductorsController.getInstance().subscribe(mqttInterface);
		} catch (MqttException e) {
			logger.error(e.getLocalizedMessage());
		} 	
	}


	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		this.setUpMqttInterface(actuatorMqttInterface);	
	}

}
