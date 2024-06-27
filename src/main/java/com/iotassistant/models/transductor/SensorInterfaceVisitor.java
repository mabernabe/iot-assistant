package com.iotassistant.models.transductor;

import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

public interface SensorInterfaceVisitor {

	void visit(SensorMqttInterface sensorMqttInterface, boolean setUp);
	
	

}
