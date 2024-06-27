package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.TransductorVisitor;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.WatchdogInterval;


@Service
public class TransductorsService implements TransductorVisitor{
	
	@Autowired
	private SensorsService sensorService;
	
	@Autowired
	private ActuatorsService actuatorService;

	
	public List<Property> getSupportedPropertiesMeasured() {
		return sensorService.getSupportedPropertiesMeasured();
	}


	public boolean existTransductor(String transductorName) {
	    return sensorService.exist(transductorName) || actuatorService.existActuator(transductorName);
	}

	public List<Property> getSupportedPropertiesActuated() {
		return actuatorService.getSupportedPropertiesActuated();
	}



	public List<Transductor> getAllTransductors() {
		List<Transductor> transductors = new ArrayList<Transductor>();
		transductors.addAll(sensorService.getAllSensors());
		transductors.addAll(actuatorService.getAllActuators());
		return transductors;
	}

	public List<String> getSupportedWatchdogIntervals() {
		return WatchdogInterval.getAvailableWatchdogIntervalOptions();
	}

	public List<String> getSupportedSensorRulesTypes() {
		return sensorService.getSupportedSensorRulesTypes();
	}


	public List<String> getSupportedSensorRulesTimeBetweenTriggers() {
		return SensorRuleTriggerIntervalEnum.getAvailableTriggerIntervalOptions();
	}


	public void setUpInterface(Transductor transductor) {
		transductor.accept(this);
	}


	@Override
	public void visit(Sensor sensor) {
		sensorService.setUpInterface(sensor);		
	}


	@Override
	public void visit(Actuator actuator) {
		actuatorService.setUpInterface(actuator);		
	}

}
