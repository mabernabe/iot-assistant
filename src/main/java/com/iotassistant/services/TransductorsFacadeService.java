package com.iotassistant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.ActuatorValues;
import com.iotassistant.models.devices.transductors.Property;
import com.iotassistant.models.devices.transductors.Sensor;
import com.iotassistant.models.devices.transductors.SensorValues;
import com.iotassistant.models.devices.transductors.Transductor;
import com.iotassistant.models.devices.transductors.TransductorInterfaceTypeEnum;
import com.iotassistant.repositories.TransductorsJPARepository;


@Service
public class TransductorsFacadeService {
	
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
	    return transductorsJPARepository.findOne(name) != null;
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

	public void setUpInterface(Sensor sensor)  {
		sensorService.setUpInterface(sensor);		
	}

	public void setUpInterface(Actuator actuator) {
		actuatorService.setUpInterface(actuator);		
	}

	public Transductor getTransductorByName(String name) {
		return transductorsJPARepository.findOne(name);
	
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
