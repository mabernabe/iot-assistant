package com.iotassistant.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.iotassistant.models.devices.Device;
import com.iotassistant.repositories.DevicesJPARepository;

public class DeviceService {
	
	@Autowired
	private DevicesJPARepository devicesJPARepository;
	
	public boolean existDevice(String name) {
		return this.getDeviceByName(name) != null;
	}
	
	protected Device getDeviceByName(String name) {
		return devicesJPARepository.findOne(name);	
	}
	
	public void enableDisableWatchdog(boolean enable, String deviceName) {
		Device device = getDeviceByName(deviceName);
		device.setWatchdogEnabled(enable);
		devicesJPARepository.save(device);	
	}
	
	protected void setUpInterface(Device device) {
		new DeviceSetUpInterfaceService().setUp(device.getInterface());	
	}
	
	protected void setDownInterface(Device device) {
		new DeviceSetDownInterfaceService().setDown(device.getInterface());;
		
	}

}
