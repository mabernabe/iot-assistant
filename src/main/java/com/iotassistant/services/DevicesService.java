package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.models.Camera;
import com.iotassistant.models.Device;
import com.iotassistant.models.DeviceVisitor;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.repositories.DevicesJPARepository;


@Component
public class DevicesService implements DeviceVisitor {
	
	@Autowired
	private TransductorsService transductorsService;
	
	@Autowired
	private CamerasService camerasService;
	
	@Autowired
	private DevicesJPARepository devicesJPARepository;
	
	private static DevicesService instance;
	
	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	public static DevicesService getInstance() {
		return instance;
	}

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

	public void setUpInterface(Device device)  {
		device.accept(this);
	}


	@Override
	public void visit(Camera camera) {
		camerasService.setUpInterface(camera);
	}

	@Override
	public void visit(Transductor transductor){
		transductorsService.setUpInterface(transductor);		
	}
	
	public void setActive(String name, boolean active) {
		assert(this.existDevice(name));
		Device device = devicesJPARepository.findById(name).get();
		device.setActive(false);
		devicesJPARepository.saveAndFlush(device);
		
	}

	public boolean existDevice(String name) {
		return devicesJPARepository.findById(name) != null;
	}
	
	public Device getDeviceByName(String name) {
		Optional<Device> device = devicesJPARepository.findById(name);
		if (device.isPresent()) {
			return device.get();
		}
		return null;	
	}

}
