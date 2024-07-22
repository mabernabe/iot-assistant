package com.iotassistant.models.devices;

import com.iotassistant.models.CameraHttpInterface;

public interface CameraInterfaceVisitor {

	public void visit(CameraHttpInterface cameraHttpInterface) throws CameraInterfaceException;
	
	

}
