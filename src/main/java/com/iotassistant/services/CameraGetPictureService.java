package com.iotassistant.services;

import com.iotassistant.controllers.HttpDevicesController;
import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.devices.CameraInterface;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.models.devices.CameraInterfaceVisitor;

class CameraGetPictureService implements CameraInterfaceVisitor{
	
	private byte[] picture;
	
	public CameraGetPictureService() {
		super();
	}
	
	byte[] getPicture(CameraInterface cameraInterface) throws CameraInterfaceException {
		cameraInterface.accept(this);
		return this.picture;
	}


	@Override
	public void visit(CameraHttpInterface cameraHttpInterface) throws CameraInterfaceException {
			this.picture = HttpDevicesController.getInstance().getCameraPicture(cameraHttpInterface);		
	}

}
