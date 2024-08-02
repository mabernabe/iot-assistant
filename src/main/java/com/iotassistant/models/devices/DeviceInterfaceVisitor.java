package com.iotassistant.models.devices;

import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.SensorMqttInterface;

public interface DeviceInterfaceVisitor {

	void visit(SensorMqttInterface sensorMqttInterface) ;

	void visit(ActuatorMqttInterface actuatorMqttInterface);
	
	void visit(CameraHttpInterface cameraHttpInterface);
	
	

}
