package com.iotassistant.models.devices;

import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.mqtt.MqttInterface;

public interface DeviceInterfaceVisitor {

	void visit(MqttInterface mqttInterface) ;
	
	void visit(CameraHttpInterface cameraHttpInterface);	
	

}
