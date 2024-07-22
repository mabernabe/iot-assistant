package com.iotassistant.models.devices;

import com.iotassistant.mqtt.ActuatorMqttInterface;

public interface ActuatorInterfaceVisitor {
	
	void visit(ActuatorMqttInterface actuatorMqttInterface);

}
