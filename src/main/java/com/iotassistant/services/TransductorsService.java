package com.iotassistant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.devices.Actuator;
import com.iotassistant.models.devices.ActuatorValues;
import com.iotassistant.models.devices.Property;
import com.iotassistant.models.devices.Sensor;
import com.iotassistant.models.devices.SensorValues;
import com.iotassistant.models.devices.Transductor;
import com.iotassistant.models.devices.TransductorInterfaceTypeEnum;
import com.iotassistant.models.devices.TransductorVisitor;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.repositories.TransductorsJPARepository;


@Service
public class TransductorsService implements TransductorVisitor{
	
	@Autowired
	private SensorsService sensorService;
	
	@Autowired
	private ActuatorsService actuatorService;
	
	@Autowired
	private TransductorsJPARepository transductorsJPARepository;
	

	
	public List<Property> getSupportedPropertiesMeasured() {
		return sensorService.getSupportedPropertiesMeasured();
	}


	public boolean existTransductor(String name) {
	    return transductorsJPARepository.findById(name).isPresent();
	}

	public List<Property> getSupportedPropertiesActuated() {
		return actuatorService.getSupportedPropertiesActuated();
	}


	public List<Transductor> getAllTransductors() {
		return transductorsJPARepository.findAll();
	}

	public List<String> getSupportedWatchdogIntervals() {
		return WatchdogInterval.getAvailableWatchdogIntervalOptions();
	}


	public List<String> getSupportedSensorRulesTimeBetweenTriggers() {
		return SensorRuleTriggerIntervalEnum.getAvailableTriggerIntervalOptions();
	}


	public void setUpInterface(Transductor transductor) {
		transductor.accept(this);
	}


	@Override
	public void visit(Sensor sensor)  {
		sensorService.setUpInterface(sensor);		
	}


	@Override
	public void visit(Actuator actuator) {
		actuatorService.setUpInterface(actuator);		
	}

	public Transductor getTransductorByName(String name) {
		Optional<Transductor> transductor = transductorsJPARepository.findById(name);
		if (transductor.isPresent()) {
			return transductor.get();
		}
		return null;		
	}


	public void updateSensorValues(String name, SensorValues sensorValues) {
		sensorService.update(name, sensorValues);	
	}


	public void updateActuatorValues(String name, ActuatorValues actuatorValues) {
		actuatorService.update(name, actuatorValues);
		
	}


	public List<String> getSupportedTransductorInterfaces() {
		return TransductorInterfaceTypeEnum.getAllInstances();
	}


}
