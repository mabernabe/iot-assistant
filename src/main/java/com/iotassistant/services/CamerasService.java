package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.models.devices.CameraInterfaceTypeEnum;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.repositories.CamerasJPARepository;

@Service
public class CamerasService extends DeviceService{
	
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
		this.setUpInterface(camera);
	}

	public List<String> getSupportedInterfaces() {
		return CameraInterfaceTypeEnum.getAllInstancesString();
	}

	public void deleteCamera(String cameraName)  {
		camerasJPARepository.delete(cameraName);
		sensorRulesService.deleteCameraSensorRules(cameraName);
		
	}

	public byte[] getPicture(String cameraName) throws CameraInterfaceException {
		assert(existCamera(cameraName));
		Camera camera = camerasJPARepository.findOne(cameraName);
		return new CameraGetPictureService().getPicture(camera.getInterface());
		
	}

	public boolean existCamera(String cameraName) {
		return camerasJPARepository.findOne(cameraName) != null;
	}


	public List<String> getSupportedWatchdogIntervals() {
		List<String> supportedWatchdogIntervals = new ArrayList<String>();
		supportedWatchdogIntervals.add(WatchdogInterval.NO_WATCHDOG.toString());
		supportedWatchdogIntervals.add(WatchdogInterval.ONE_MINUTE.toString());
		return supportedWatchdogIntervals;
	}

}
