package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.HashMap;
import java.util.Map;

import com.iotassistant.models.devices.SensorValues;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;


class SensorValuesDTO {
	
	private Map<String, SensorValueDTO> values;
	
	private String date;
	
	SensorValuesDTO(SensorValues sensorValues) {
		this.date = sensorValues.getDate();
		this.values = new HashMap<String, SensorValueDTO>();
		for (PropertyMeasuredEnum propertyMeasured: sensorValues.getValues().keySet()) {
			SensorValueDTO sensorValueDTO = new SensorValueDTO(propertyMeasured, sensorValues.getValues().get(propertyMeasured));
			this.values.put(propertyMeasured.getNameWithUnit(), sensorValueDTO);
		}
	}

	public Map<String, SensorValueDTO> getValues() {
		return values;
	}

	public String getDate() {
		return date;
	}

}
