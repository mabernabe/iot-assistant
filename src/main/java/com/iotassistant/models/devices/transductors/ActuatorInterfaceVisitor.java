package com.iotassistant.models.devices.transductors;

import com.iotassistant.mqtt.MqttActuatorInterface;

public interface ActuatorInterfaceVisitor {
	
	void visit(MqttActuatorInterface actuatorMqttInterface);

}
