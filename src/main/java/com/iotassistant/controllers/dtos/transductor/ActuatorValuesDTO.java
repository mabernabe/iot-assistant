package com.iotassistant.controllers.dtos.transductor;

import java.util.HashMap;
import java.util.Map;

import com.iotassistant.models.transductor.ActuatorValues;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class ActuatorValuesDTO  {
	
	private Map<String, ActuatorValueDTO> values;
	
	private String date;

	public ActuatorValuesDTO(ActuatorValues actuatorValues) {
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
