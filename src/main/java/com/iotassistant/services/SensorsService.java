package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.repositories.SensorsJPARepository;

@Service
@Transactional
public class SensorsService  {
	
	@Autowired
	private SensorsJPARepository sensorsRepository;
	
	@Autowired
	private ChartsService chartsService;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Autowired
	private TransductorSetUpInterfaceService transductorSetUpInterfaceService;
	
	@Autowired
	private TransductorSetDownInterfaceService transductorSetDownInterfaceService;
	
	
	public Sensor getSensorByName(String name) {
		Optional<Sensor> sensor = sensorsRepository.findById(name);
		if (sensor.isPresent()) {
			return sensor.get();
		}
		return null;	
	}
	
	public List<Sensor> getAllSensors() {
		return sensorsRepository.findAll();
	}

	public Sensor newSensor(Sensor sensor) {
		sensor = sensorsRepository.save(sensor);
		this.setUpInterface(sensor);
		return sensor;
	}
	
	
	public List<Property> getSupportedPropertiesMeasured() {
		List<Property> propertiesMeasured = new ArrayList<>();
		for (PropertyMeasuredEnum propertyMeasured: PropertyMeasuredEnum.ALL_INSTANCES) {
			propertiesMeasured.add(propertyMeasured);
		}
		return propertiesMeasured;
	}

	public void deleteSensorByName(String name)  {
		Sensor sensor = getSensorByName(name);
		this.setDownInterface(sensor);
		deleteSensorDependencies(name);
		sensorsRepository.deleteById(name);
			
	}

	private void setDownInterface(Sensor sensor) {
		transductorSetDownInterfaceService.setDown(sensor.getInterface());
		
	}

	private void deleteSensorDependencies(String sensorName) {
		chartsService.deleteChartsBySensorName(sensorName);	
		sensorRulesService.deleteSensorRuleBySensorName(sensorName);
	}
	

	public boolean exist(String sensorName) {
		return this.getSensorByName(sensorName) != null;
	}
	
	public boolean hasSensorProperty(String sensorName, PropertyMeasuredEnum propertyObserved) {
		assert(exist(sensorName));
		boolean hasSensorProperty = true;
		Sensor sensor = getSensorByName(sensorName);
		if (!sensor.getPropertiesMeasured().contains(propertyObserved)) {
			hasSensorProperty = false;
		}
		return hasSensorProperty;
	}


	public void enableDisableWatchdog(boolean enable, String sensorName) {
		Sensor sensor = getSensorByName(sensorName);
		sensor.setWatchdogEnabled(enable);
		sensorsRepository.save(sensor);	
	}

	public void setUpInterface(Sensor sensor) {
		transductorSetUpInterfaceService.setUp(sensor.getInterface());	
	}

	public void update(String name, SensorValues values) {
		Sensor sensor = this.getSensorByName(name);
		assert(sensor!=null);
		sensor.setValues(values);
		sensor.setActive(true);
		sensorsRepository.saveAndFlush(sensor);
		sensorRulesService.applyRules(sensor.getName(), values);	
		
	}

}
