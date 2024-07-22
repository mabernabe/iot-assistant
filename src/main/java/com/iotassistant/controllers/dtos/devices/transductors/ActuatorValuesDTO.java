package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.HashMap;
import java.util.Map;

import com.iotassistant.models.devices.ActuatorValues;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

class ActuatorValuesDTO  {
	
	private Map<String, ActuatorValueDTO> values;
	
	private String date;

	ActuatorValuesDTO(ActuatorValues actuatorValues) {
		this.date = actuatorValues.getDate();
		this.values = new HashMap<String, ActuatorValueDTO>();
		for (PropertyActuatedEnum propertyActuated: actuatorValues.getValues().keySet()) {
			ActuatorValueDTO sensorValueDTO = new ActuatorValueDTO(propertyActuated, actuatorValues.getValues().get(propertyActuated));
			this.values.put(propertyActuated.getNameWithUnit(), sensorValueDTO);
		}
	}

	public Map<String, ActuatorValueDTO> getValues() {
		return values;
	}

	public String getDate() {
		return date;
	}
}
