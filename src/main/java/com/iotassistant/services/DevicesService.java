package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.models.Camera;
import com.iotassistant.models.Device;
import com.iotassistant.models.transductor.Transductor;


@Component
public class DevicesService {
	
	@Autowired
	private TransductorsService transductorsService;
	
	@Autowired
	private CamerasService camerasService;

	public List<Device> getAllDevices() {
		List<Device> allDevices = new ArrayList<Device>();
		for (Transductor transductor : transductorsService.getAllTransductors()) {
			allDevices.add(transductor);
		}
		for (Camera camera : camerasService.getAllCameras()) {
			allDevices.add(camera);
		}
		return allDevices;		
	}

}
