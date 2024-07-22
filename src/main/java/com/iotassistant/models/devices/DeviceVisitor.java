package com.iotassistant.models.devices;

public interface DeviceVisitor {

	public void visit(Transductor transductor);
	
	public void visit(Camera camera);

}
