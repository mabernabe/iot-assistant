package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.devices.DeviceInterface;
import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.mqtt.MqttDevicesController;
import com.iotassistant.mqtt.MqttInterface;

class DeviceSetDownInterfaceService  implements DeviceInterfaceVisitor {
	
	Logger logger = LoggerFactory.getLogger(DeviceSetDownInterfaceService.class);

	public DeviceSetDownInterfaceService() {
		super();	
	}

	public void setDown(DeviceInterface transductorInterface)  {
		transductorInterface.accept(this);	
	}
	
	@Override
	public void visit(MqttInterface mqttInterface) {		
		try {
			MqttDevicesController.getInstance().unsubscribe(mqttInterface);
		} catch (MqttException e) {
			logger.error(e.getLocalizedMessage());
		}		
	}

	
	@Override
	public void visit(CameraHttpInterface cameraHttpInterface) {
		// Nothing to do
		
	}

}
