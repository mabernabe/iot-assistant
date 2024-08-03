package com.iotassistant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iotassistant.models.devices.Device;
import com.iotassistant.services.DevicesFacadeService;

@Component
public class IoTAssistantStartupRunner implements CommandLineRunner{

	@Autowired
	private DevicesFacadeService devicesService;
	
	@Override
	public void run(String...args) throws Exception {
		setUpDevicesInterfaces();
	}

	private void setUpDevicesInterfaces()  {
		List<Device> allDevices = devicesService.getAllDevices();
        for (Device device : allDevices) {
        		devicesService.setUpInterface(device);
        }	
	}

}