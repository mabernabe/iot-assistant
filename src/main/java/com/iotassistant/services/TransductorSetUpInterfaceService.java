package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.MqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;


public class TransductorSetUpInterfaceService implements TransductorInterfaceVisitor {

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
			MQTTTransductorsController.getInstance().subscribe(mqttInterface);
		} catch (MqttException e) {
			DevicesService.getInstance().setActive(mqttInterface.getTopic(), false);
		} 	
	}


	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		this.setUpMqttInterface(actuatorMqttInterface);	
	}

}
