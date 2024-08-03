package com.iotassistant.models.devices;

import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.Sensor;

public interface DeviceVisitor {

	public void visit(Sensor sensor);
	
	public void visit(Actuator actuator);
	
	public void visit(Camera camera);
	
	public void visit(Gps gps);

}
