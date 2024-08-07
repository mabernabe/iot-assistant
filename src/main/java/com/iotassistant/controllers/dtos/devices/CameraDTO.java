package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.controllers.CamerasController;
import com.iotassistant.models.devices.Camera;

class CameraDTO extends DeviceDTO{
		
	private String urlPicture;

	
	public CameraDTO() {
		super();
	}
	

	CameraDTO(Camera camera,  String baseUrlCameraPicture) {
		super(camera);
		this.urlPicture = baseUrlCameraPicture + camera.getName() + CamerasController.CAMERA_PICTURE_URL;
	}


	public String getUrlPicture() {
		return urlPicture;
	}

	
	
	

}
