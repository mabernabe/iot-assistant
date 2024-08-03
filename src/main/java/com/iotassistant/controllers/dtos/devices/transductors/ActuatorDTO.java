package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

class ActuatorDTO extends TransductorDTO{
	
	private ActuatorValuesDTO actuatorValues;
	
	private List<PropertyActuatedDTO> propertiesActuated;
	

	ActuatorDTO(Actuator actuator) {
		super(actuator);
		this.actuatorValues = actuator.isActive() ? new ActuatorValuesDTO(actuator.getValues()) : null;
		this.propertiesActuated = new ArrayList<PropertyActuatedDTO>();
		for (PropertyActuatedEnum propertyActuated : actuator.getPropertiesActuated()) {
			this.propertiesActuated.add(new PropertyActuatedDTO(propertyActuated));
		}
		
	}


	public ActuatorValuesDTO getActuatorValues() {
		return actuatorValues;
	}


	public List<PropertyActuatedDTO> getPropertiesActuated() {
		return propertiesActuated;
	}


	

}
