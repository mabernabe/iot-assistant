package com.iotassistant.controllers.dtos.transductor;

import java.util.HashMap;
import java.util.Map;

import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;


public class SensorValuesDTO {
	
	private Map<String, String> values;
	
	private String date;
	
	public SensorValuesDTO(SensorValues sensorValues) {
		this.date = sensorValues.getDate();
		this.values = new HashMap<String, String>();
		for (PropertyMeasuredEnum propertyMeasured: sensorValues.getValues().keySet()) {
			this.values.put(propertyMeasured.toString(), sensorValues.getValues().get(propertyMeasured).toString());
		}
	}

	public Map<String, String> getValues() {
		return values;
	}

	public String getDate() {
		return date;
	}

}
