package com.iotassistant.controllers.dtos.transductor;

import java.util.HashMap;
import java.util.Map;

import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;


public class SensorValuesDTO {
	
	private Map<String, SensorValueDTO> values;
	
	private String date;
	
	public SensorValuesDTO(SensorValues sensorValues) {
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
