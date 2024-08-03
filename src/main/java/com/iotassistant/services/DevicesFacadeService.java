package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.models.devices.Device;
import com.iotassistant.models.devices.DeviceVisitor;
import com.iotassistant.models.devices.Gps;
import com.iotassistant.models.devices.GpsPosition;
import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.ActuatorValues;
import com.iotassistant.models.devices.transductors.Sensor;
import com.iotassistant.models.devices.transductors.SensorValues;
import com.iotassistant.models.devices.transductors.Transductor;
import com.iotassistant.repositories.DevicesJPARepository;


@Component
public class DevicesFacadeService implements DeviceVisitor {
	
	@Autowired
	private TransductorsFacadeService transductorsService;
	
	@Autowired
	private CamerasService camerasService;
	
	@Autowired
	private GpsesService gpsesService;
	
	@Autowired
	private DevicesJPARepository devicesJPARepository;
	
	private static DevicesFacadeService instance;
	
	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	static DevicesFacadeService getInstance() {
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
	public void visit(Sensor sensor) {
		transductorsService.setUpInterface(sensor);	
	}
	
	@Override
	public void visit(Actuator actuator) {
		transductorsService.setUpInterface(actuator);		
	}
	
	@Override
	public void visit(Gps gps) {
		gpsesService.setUpInterface(gps);
	}
	
	public Device getDeviceByName(String name) {
		return devicesJPARepository.findOne(name);	
	}
	
	public byte[] getCameraPicture(Camera camera) throws CameraInterfaceException {
		return camerasService.getPicture(camera.getName());
		
	}
	
	public void updateSensorValues(String sensorName, SensorValues sensorValues) {
		transductorsService.updateSensorValues(sensorName, sensorValues);	
	}
	
	public void updateActuatorValues(String actuatorName, ActuatorValues actuatorValues) {
		transductorsService.updateActuatorValues(actuatorName, actuatorValues);
	}
	
	public void updateGpsPosition(String gpsName, GpsPosition position) {
		gpsesService.update(gpsName, position);
	}
	

}
