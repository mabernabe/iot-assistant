package com.iotassistant.models;

public interface CameraInterfaceVisitor {

	public void visit(CameraHttpInterface cameraHttpInterface) throws CameraInterfaceException;
	
	

}
