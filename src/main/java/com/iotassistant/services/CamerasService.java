package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.Camera;
import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.CameraInterfaceTypeEnum;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.repositories.CamerasJPARepository;

@Service
public class CamerasService {
	
	private static CamerasService instance;

	@Autowired
	private CamerasJPARepository camerasJPARepository;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 

	public static CamerasService getInstance() {
		return instance;
	}

	public List<Camera> getAllCameras() {
		return camerasJPARepository.findAll();	
	}

	public void newCamera(Camera camera) {
		camerasJPARepository.save(camera);
	}

	public List<String> getSupportedInterfaces() {
		return CameraInterfaceTypeEnum.getAllInstancesString();
	}

	public void deleteCamera(String cameraName)  {
		camerasJPARepository.deleteById(cameraName);
		sensorRulesService.deleteCameraSensorRules(cameraName);
		
	}

	public byte[] getPicture(String cameraName) throws CameraInterfaceException {
		assert(existCamera(cameraName));
		Camera camera = camerasJPARepository.findById(cameraName).get();
		return new CameraGetPictureService().getPicture(camera.getInterface());
		
	}

	public boolean existCamera(String cameraName) {
		return camerasJPARepository.findById(cameraName).isPresent();
	}

	public void setUpInterface(Camera camera) {
		camera.getInterface();
		
	}

	public List<String> getSupportedWatchdogIntervals() {
		List<String> supportedWatchdogIntervals = new ArrayList<String>();
		supportedWatchdogIntervals.add(WatchdogInterval.NO_WATCHDOG.toString());
		supportedWatchdogIntervals.add(WatchdogInterval.ONE_MINUTE.toString());
		return supportedWatchdogIntervals;
	}

}
