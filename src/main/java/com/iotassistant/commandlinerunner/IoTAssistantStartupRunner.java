package com.iotassistant.commandlinerunner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iotassistant.models.Device;
import com.iotassistant.services.DevicesService;

@Component
public class IoTAssistantStartupRunner implements CommandLineRunner{

	@Autowired
	private DevicesService devicesService;
	
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