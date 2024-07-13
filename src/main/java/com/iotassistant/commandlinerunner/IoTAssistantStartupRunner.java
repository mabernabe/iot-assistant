package com.iotassistant.commandlinerunner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iotassistant.models.Device;
import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.services.DevicesService;
import com.iotassistant.services.SensorRulesService;

@Component
public class IoTAssistantStartupRunner implements CommandLineRunner{

	@Autowired
	private DevicesService devicesService;
	
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Override
	public void run(String...args) throws Exception {
		setUpDevicesInterfaces();
		setupSensorRules();
	}

	private void setupSensorRules() {
		List<SensorRule> sensorRules = sensorRulesService.getAllSensorRules(); 
        for (SensorRule sensorRule : sensorRules) {		
        	try {
        		sensorRulesService.setupSensorRule(sensorRule);
			} catch (Exception e) {
			}
        }
		
	}


	private void setUpDevicesInterfaces()  {
		List<Device> allDevices = devicesService.getAllDevices();
        for (Device device : allDevices) {
        		devicesService.setUpInterface(device);
        }	
	}

}