package com.iotassistant.models.transductor;

import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;

public interface ActuatorInterfaceVisitor {
	
	void visit(ActuatorMqttInterface actuatorMqttInterface);

}
