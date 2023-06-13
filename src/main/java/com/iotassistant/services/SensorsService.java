package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.sensorrules.SensorRuleType;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.repositories.SensorsRepository;

@Service
@Transactional
public class SensorsService {
	
	@Autowired
	private SensorsRepository sensorsRepository;
	
	@Autowired
	private ChartsService chartsService;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	
	
	public Sensor getSensorByName(String name) {
		return sensorsRepository.getSensorByName(name);
	}
	
	public List<Sensor> getAllSensors() {
		return sensorsRepository.getAllSensors();
	}

	public Sensor newSensor(Sensor sensor) throws TransductorInterfaceException {
		sensor = sensorsRepository.save(sensor);
		sensor.setUpInterface();
		return sensor;
	}
	
	
	public List<Property> getSupportedPropertiesMeasured() {
		List<Property> propertiesMeasured = new ArrayList<>();
		for (PropertyMeasuredEnum propertyMeasured: PropertyMeasuredEnum.ALL_INSTANCES) {
			propertiesMeasured.add(propertyMeasured);
		}
		return propertiesMeasured;
	}

	public void deleteSensorByName(String name) throws TransductorInterfaceException {
		Sensor sensor = getSensorByName(name);
		sensor.setDownInterface();
		deleteSensorDependencies(name);
		sensorsRepository.deleteSensorByName(name);
			
	}

	private void deleteSensorDependencies(String sensorName) throws TransductorInterfaceException {
		chartsService.deleteChartsBySensorName(sensorName);	
		sensorRulesService.deleteSensorRuleBySensorName(sensorName);
	}

	public void registerSensorMeasureObserver(SensorMeasureObserver sensorMeasureObserver) throws TransductorInterfaceException  {
		Sensor sensor = getSensorByName(sensorMeasureObserver.getSensorName());
		sensor.registerMeasureObserver(sensorMeasureObserver);

	}

	public void unregisterSensorMeasureObserver(SensorMeasureObserver sensorMeasureObserver) throws TransductorInterfaceException  {
		Sensor sensor = getSensorByName(sensorMeasureObserver.getSensorName());
		sensor.unRegisterMeasureObserver(sensorMeasureObserver);	
	}
	

	public List<String> getSupportedSensorRulesTypes() {
		return SensorRuleType.getAllInstances();
	}

	public boolean exist(String sensorName) {
		return sensorsRepository.getSensorByName(sensorName) != null;
	}
	
	public boolean hasSensorProperty(String sensorName, PropertyMeasuredEnum propertyObserved) {
		assert(exist(sensorName));
		boolean hasSensorProperty = true;
		Sensor sensor = getSensorByName(sensorName);
		if (!sensor.getProperties().contains(propertyObserved)) {
			hasSensorProperty = false;
		}
		return hasSensorProperty;
	}


	public void enableDisableWatchdog(boolean enable, String sensorName) {
		Sensor sensor = getSensorByName(sensorName);
		sensor.setWatchdogEnabled(enable);
		sensorsRepository.update(sensor);	
	}
	

}
