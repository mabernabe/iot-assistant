package com.iotassistant.models.devices;

public interface TransductorVisitor {
	
	public void visit(Sensor sensor) ;
	
	public void visit(Actuator actuator) ;

}
