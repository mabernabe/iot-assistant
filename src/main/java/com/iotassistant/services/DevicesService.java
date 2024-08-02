package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.models.devices.Actuator;
import com.iotassistant.models.devices.ActuatorValues;
import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.models.devices.Device;
import com.iotassistant.models.devices.DeviceVisitor;
import com.iotassistant.models.devices.Sensor;
import com.iotassistant.models.devices.SensorValues;
import com.iotassistant.models.devices.Transductor;
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
	static DevicesService getInstance() {
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

	
	void setActive(String name, boolean active) {
		assert(this.existDevice(name));
		Device device = devicesJPARepository.findOne(name);
		device.setActive(false);
		devicesJPARepository.saveAndFlush(device);
		
	}

	public boolean existDevice(String name) {
		return this.getDeviceByName(name) != null;
	}
	
	public Device getDeviceByName(String name) {
		return devicesJPARepository.findOne(name);	
	}
	
	public byte[] getCameraPicture(Camera camera) throws CameraInterfaceException {
		return camerasService.getPicture(camera.getName());
		
	}
	@Override
	public void visit(Sensor sensor) {
		transductorsService.setUpInterface(sensor);	
		
	}
	@Override
	public void visit(Actuator actuator) {
		transductorsService.setUpInterface(actuator);	
		
	}
	public void updateSensorValues(String sensorName, SensorValues sensorValues) {
		transductorsService.updateSensorValues(sensorName, sensorValues);
		
	}
	public void updateActuatorValues(String actuatorName, ActuatorValues actuatorValues) {
		transductorsService.updateActuatorValues(actuatorName, actuatorValues);
		
	}
	

}
