package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.ActuatorValues;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.repositories.ActuatorsJPARepository;

@Service
@Transactional
public class ActuatorsService {
	
	@Autowired
	ActuatorsJPARepository actuatorsRepository;
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Autowired
	private TransductorSetUpInterfaceService transductorSetUpInterfaceService;
	
	@Autowired
	private TransductorSetDownInterfaceService transductorSetDownInterfaceService;
	
	@Autowired
	private ActuatorSetValueService actuatorSetValueService;
	
	public Actuator newActuator(Actuator actuator)  {
		actuator = actuatorsRepository.save(actuator);
		this.setUpInterface(actuator);
		return actuator;	
	}

	
	public void setActuatorValue(PropertyActuatedEnum propertyActuated, String actuatorName, String value)  {
		//Actuator actuator = actuatorsRepository.getActuatorByName(actuatorName);
		//actuator.setValue(propertyActuated, value);
		
	}
	
	public List<Actuator> getAllActuators() {
		return actuatorsRepository.findAll();
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

	public Actuator getActuatorByName(String name) {
		Optional<Actuator> actuator = actuatorsRepository.findById(name);
		if (actuator.isPresent()) {
			return actuator.get();
		}
		return null;	
	}


	public void deleteActuatorByName(String name){
		Actuator actuator = getActuatorByName(name);
		this.setDownInterface(actuator);
		deleteActuatorDependencies(name);
		actuatorsRepository.deleteById(name);		
	}

	private void setDownInterface(Actuator actuator) {
		transductorSetDownInterfaceService.setDown(actuator.getInterface());
		
	}


	private void deleteActuatorDependencies(String actuatorName) {
		sensorRulesService.deleteTriggerActuatorSensorRules(actuatorName);		
	}
	
	
	public boolean hasActuatorProperty(String actuatorName, PropertyActuatedEnum propertyActuated) {
		assert(existActuator(actuatorName));
		boolean hasActuatorProperty = true;
		Actuator actuator = getActuatorByName(actuatorName);
		if (propertyActuated == null || !actuator.getPropertiesActuated().contains(propertyActuated)) {
			hasActuatorProperty = false;
		}
		return hasActuatorProperty;
	}


	public void enableDisableWatchdog(boolean enable, String actuatorName) {
		Actuator actuator = getActuatorByName(actuatorName);
		actuator.setWatchdogEnabled(enable);
		actuatorsRepository.save(actuator);	
	}


	public void setUpInterface(Actuator actuator) {
		transductorSetUpInterfaceService.setUp(actuator.getInterface());
		
	}


	public void update(String name, ActuatorValues actuatorValues) {
		Actuator actuator = this.getActuatorByName(name);
		assert(actuator!=null);
		actuator.setValues(actuatorValues);
		actuator.setActive(true);
		actuatorsRepository.saveAndFlush(actuator);
	}

	

}
