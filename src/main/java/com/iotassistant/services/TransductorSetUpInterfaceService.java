package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.MqttInterface;
import com.iotassistant.mqtt.MqttTransductorsController;
import com.iotassistant.mqtt.SensorMqttInterface;


class TransductorSetUpInterfaceService implements TransductorInterfaceVisitor {

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
			e.printStackTrace();
			DevicesService.getInstance().setActive(mqttInterface.getTopic(), false);
		} 	
	}


	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		this.setUpMqttInterface(actuatorMqttInterface);	
	}

}
