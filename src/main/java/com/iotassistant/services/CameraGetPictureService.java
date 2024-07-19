package com.iotassistant.services;

import com.iotassistant.controllers.HttpDevicesController;
import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.CameraInterface;
import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.CameraInterfaceVisitor;

public class CameraGetPictureService implements CameraInterfaceVisitor{
	
	private byte[] picture;
	
	public CameraGetPictureService() {
		super();
	}
	
	public byte[] getPicture(CameraInterface cameraInterface) throws CameraInterfaceException {
		cameraInterface.accept(this);
		return this.picture;
	}


	@Override
	public void visit(CameraHttpInterface cameraHttpInterface) throws CameraInterfaceException {
			this.picture = HttpDevicesController.getInstance().getCameraPicture(cameraHttpInterface);		
	}

}
