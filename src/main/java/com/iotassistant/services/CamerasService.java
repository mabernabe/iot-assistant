package com.iotassistant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.Camera;
import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.CameraInterfaceTypeEnum;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.repositories.CamerasRepository;

@Service
public class CamerasService {
	
	@Autowired
	CamerasRepository camerasRepository;
	
	@Autowired
	private SensorRulesService sensorRulesService;

	public List<Camera> getAllCameras() {
		return camerasRepository.getAllCameras();	
	}

	public void newHTTPCamera(Camera camera) {
		camerasRepository.save(camera);
	}

	public List<String> getSupportedInterfaces() {
		return CameraInterfaceTypeEnum.getAllInstancesString();
	}

	public void deleteCamera(String cameraName) throws TransductorInterfaceException {
		camerasRepository.deleteCameraByName(cameraName);
		sensorRulesService.deleteCameraSensorRules(cameraName);
		
	}

	public byte[] getPicture(String name) throws CameraInterfaceException {
		return camerasRepository.getCameraByName(name).getPicture();
		
	}

	public boolean existCamera(String cameraName) {
		return camerasRepository.getCameraByName(cameraName) != null;
	}

	public void setUpInterface(Camera camera) {
		camera.getInterface();
		
	}



}
