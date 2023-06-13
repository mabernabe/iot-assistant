package com.iotassistant.controllers.dtos;

import com.iotassistant.controllers.CamerasController;
import com.iotassistant.models.Camera;

public class CameraDTO extends DeviceDTO{
		
	protected String urlPicture;

	
	public CameraDTO() {
		super();
	}
	

	public CameraDTO(Camera camera,  String baseUrlCameraPicture) {
		super(camera);
		this.urlPicture = baseUrlCameraPicture + camera.getName() + CamerasController.CAMERA_PICTURE_URL;
	}


	public String getUrlPicture() {
		return urlPicture;
	}

	
	
	

}
