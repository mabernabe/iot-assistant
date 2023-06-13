package com.iotassistant.commandlinerunner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.services.SensorRulesService;
import com.iotassistant.services.TransductorsService;

@Component
public class IoTAssistantStartupRunner implements CommandLineRunner{

	@Autowired
	private TransductorsService transductorService;
	
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Override
	public void run(String...args) throws Exception {
		setUpTransductorInterfaces();
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


	private void setUpTransductorInterfaces() {
		List<Transductor> allTransductors = transductorService.getAllTransductors();
        for (Transductor transductor : allTransductors) {
        	try {
				transductor.setUpInterface();
			} catch (TransductorInterfaceException e) {
			}
        }	
	}
	

}