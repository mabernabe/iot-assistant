package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.repositories.ActuatorsRepository;

@Service
@Transactional
public class ActuatorsService {
	
	@Autowired
	ActuatorsRepository actuatorsRepository;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	public Actuator newActuator(Actuator actuator) throws TransductorInterfaceException {
		actuator = actuatorsRepository.save(actuator);
		this.setUpInterface(actuator);
		return actuator;	
	}

	
	public void setActuatorValue(PropertyActuatedEnum propertyActuated, String actuatorName, String value) throws TransductorInterfaceException {
		Actuator actuator = actuatorsRepository.getActuatorByName(actuatorName);
		actuator.setValue(propertyActuated, value);
		
	}
	
	public List<Actuator> getAllActuators() {
		return actuatorsRepository.getAllActuators();
	}

	public List<Property> getSupportedPropertiesActuated() {
		List<Property> propertiesMeasured = new ArrayList<>();
		for (PropertyActuatedEnum propertyMeasured: PropertyActuatedEnum.ALL_INSTANCES) {
			propertiesMeasured.add(propertyMeasured);
		}
		return propertiesMeasured;
	}

	public boolean existActuator(String actuatorName) {
		return this.getActuatorByName(actuatorName) != null;
	}

	public Actuator getActuatorByName(String actuatorName) {
		return actuatorsRepository.getActuatorByName(actuatorName);
	}


	public void deleteActuatorByName(String name) throws TransductorInterfaceException{
		Actuator actuator = getActuatorByName(name);
		this.setDownInterface(actuator);
		deleteActuatorDependencies(name);
		actuatorsRepository.deleteByName(name);		
	}

	private void setDownInterface(Actuator actuator) {
		// TODO Auto-generated method stub
		
	}


	private void deleteActuatorDependencies(String actuatorName) throws TransductorInterfaceException {
		sensorRulesService.deleteTriggerActuatorSensorRules(actuatorName);		
	}
	
	
	public boolean hasActuatorProperty(String actuatorName, PropertyActuatedEnum propertyActuated) {
		assert(existActuator(actuatorName));
		boolean hasActuatorProperty = true;
		Actuator actuator = getActuatorByName(actuatorName);
		if (!actuator.getProperties().contains(propertyActuated)) {
			hasActuatorProperty = false;
		}
		return hasActuatorProperty;
	}


	public void enableDisableWatchdog(boolean enable, String actuatorName) {
		Actuator actuator = getActuatorByName(actuatorName);
		actuator.setWatchdogEnabled(enable);
		actuatorsRepository.update(actuator);	
	}


	public void setUpInterface(Actuator actuator) {
		// TODO Auto-generated method stub
		
	}

	

}
