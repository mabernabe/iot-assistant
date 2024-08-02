package com.iotassistant.models.devices;

public interface DeviceVisitor {

	public void visit(Sensor sensor);
	
	public void visit(Actuator actuator);
	
	public void visit(Camera camera);

}
