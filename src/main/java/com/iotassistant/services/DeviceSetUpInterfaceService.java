package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iotassistant.models.devices.TransductorInterface;
import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.MqttInterface;
import com.iotassistant.mqtt.MqttDevicesController;
import com.iotassistant.mqtt.SensorMqttInterface;


class DeviceSetUpInterfaceService implements DeviceInterfaceVisitor {

	Logger logger = LoggerFactory.getLogger(DeviceSetUpInterfaceService.class);
	
	public DeviceSetUpInterfaceService() {
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
			MqttDevicesController.getInstance().subscribe(mqttInterface);
		} catch (MqttException e) {
			logger.error(e.getLocalizedMessage());
		} 	
	}


	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		this.setUpMqttInterface(actuatorMqttInterface);	
	}

	@Override
	public void visit(CameraHttpInterface cameraHttpInterface) {
		// Nothing to do	
	}

}
