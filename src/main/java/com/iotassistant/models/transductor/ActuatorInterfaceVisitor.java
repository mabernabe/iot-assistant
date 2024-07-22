package com.iotassistant.models.transductor;

import com.iotassistant.mqtt.ActuatorMqttInterface;

public interface ActuatorInterfaceVisitor {
	
	void visit(ActuatorMqttInterface actuatorMqttInterface);

}
