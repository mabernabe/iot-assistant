package com.iotassistant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.utils.Date;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//Sensor Values JSON Example : { "values" : {"TEMPERATURE_CENTIGRADES" : "25"}, "date" : "2022-03-23 15:41:03"}
public class MQTTSensorValuesDTO {
	
	private SensorValues sensorValues;
	
	private List<String> errors;
	
		
	@JsonCreator
	public MQTTSensorValuesDTO(@JsonProperty(value = "values" , required = true) HashMap<String, String> values, @JsonProperty(value = "date", required = true) String date){
		super();
		this.errors = new ArrayList<String>();
		deserialize(values, date);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}


	private void deserialize(HashMap<String, String> values, String date)  {
		if (!Date.isValidDate(date)) {
			this.errors.add("Invalid date");
		}
		if (values.isEmpty()) {
			this.errors.add("values is empty");
		}
		this.sensorValues = new SensorValues(new HashMap<PropertyMeasuredEnum, String>(), date);
		for(String propertyString : values.keySet() ) {
			PropertyMeasuredEnum propertyMeasuredEnum = PropertyMeasuredEnum.getInstance(propertyString);
			if (propertyMeasuredEnum == null) {
				errors.add("Unknown property measured " + propertyString);
			} else {
				String value = values.get(propertyString);
				this.sensorValues.getValues().put(propertyMeasuredEnum, value);
				if (!propertyMeasuredEnum.isValidValue(value)) {
					this.errors.add("Value is invalid :" + value);	
				}
			}
		}		
	}


	public SensorValues getValues() {
		assert(!this.hasErrors());
		return sensorValues;
	}


}
