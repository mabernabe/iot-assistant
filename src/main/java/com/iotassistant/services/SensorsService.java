package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.sensorrules.SensorRuleType;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.SensorInterfaceVisitor;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;
import com.iotassistant.repositories.SensorsJPARepository;

@Service
@Transactional
public class SensorsService implements SensorInterfaceVisitor {
	
	@Autowired
	private SensorsJPARepository sensorsRepository;
	
	@Autowired
	private ChartsService chartsService;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Autowired
	private MQTTTransductorsController mqttTransductorsController;
	
	public Sensor getSensorByName(String name) {
		return sensorsRepository.getOne(name);
	}
	
	public List<Sensor> getAllSensors() {
		return sensorsRepository.findAll();
	}

	public Sensor newSensor(Sensor sensor) throws TransductorInterfaceException {
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

	public void deleteSensorByName(String name) throws TransductorInterfaceException {
		Sensor sensor = getSensorByName(name);
		this.setDownInterface(sensor);
		deleteSensorDependencies(name);
		sensorsRepository.deleteById(name);
			
	}

	private void setDownInterface(Sensor sensor) {
		sensor.getInterface().accept(this, false);
		
	}

	private void deleteSensorDependencies(String sensorName) throws TransductorInterfaceException {
		chartsService.deleteChartsBySensorName(sensorName);	
		sensorRulesService.deleteSensorRuleBySensorName(sensorName);
	}
	

	public List<String> getSupportedSensorRulesTypes() {
		return SensorRuleType.getAllInstances();
	}

	public boolean exist(String sensorName) {
		return this.getSensorByName(sensorName) != null;
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
		sensorsRepository.save(sensor);	
	}

	public void setUpInterface(Sensor sensor) {
		sensor.getInterface().accept(this, true);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface, boolean setUp) {
		try {
			if (setUp) {
				mqttTransductorsController.subscribe(sensorMqttInterface);
			}
			else {
				mqttTransductorsController.unsubscribe(sensorMqttInterface);
			}
		} catch (MqttException e) {
			this.getSensorByName(sensorMqttInterface.getTopic().getBaseTopic()).setActive(false);
		}
		
	}

	public void update(String topic, SensorValues values) {
		Sensor sensor = this.getSensorByName(topic);
		assert(sensor!=null);
		sensor.setValues(values);
		sensorsRepository.saveAndFlush(sensor);
		sensorRulesService.applyRules(sensor.getName(), values);	
		
	}

	

}
