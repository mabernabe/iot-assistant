package com.iotassistant.models.transductor;

import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

public interface TransductorInterfaceVisitor {

	void visit(SensorMqttInterface sensorMqttInterface) ;

	void visit(ActuatorMqttInterface actuatorMqttInterface);
	
	

}
