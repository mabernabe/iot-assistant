package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.Camera;

public class CamerasDTO {
	
	private List<CameraDTO> cameras;

	public CamerasDTO(List<Camera> allCameras, String baseUrlCameraPicture) {
		this.cameras = new ArrayList<CameraDTO>();
		for (Camera camera: allCameras) {
			this.cameras.add(new CameraDTO(camera, baseUrlCameraPicture) );
		}
	}

	public List<CameraDTO> getCameras() {
		return cameras;
	}
	
	

}
