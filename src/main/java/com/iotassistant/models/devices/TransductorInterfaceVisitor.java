package com.iotassistant.models.devices;

import com.iotassistant.mqtt.ActuatorMqttInterface;
import com.iotassistant.mqtt.SensorMqttInterface;

public interface TransductorInterfaceVisitor {

	void visit(SensorMqttInterface sensorMqttInterface) ;

	void visit(ActuatorMqttInterface actuatorMqttInterface);
	
	

}
