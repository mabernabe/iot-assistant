package com.iotassistant.models.devices.transductors;

import com.iotassistant.mqtt.ActuatorMqttInterface;

public interface ActuatorInterfaceVisitor {
	
	void visit(ActuatorMqttInterface actuatorMqttInterface);

}
