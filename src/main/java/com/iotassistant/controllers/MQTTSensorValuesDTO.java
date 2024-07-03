package com.iotassistant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.utils.Date;

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
	
	public boolean hasErrors(List<PropertyMeasuredEnum> sensorProperties) {
		Set<PropertyMeasuredEnum> dtoProperties = this.sensorValues.getValues().keySet();
		for (PropertyMeasuredEnum sensorProperty: sensorProperties) {
			if (!dtoProperties.contains(sensorProperty)) {
				errors.add(sensorProperty.getNameWithUnit() + " not found in DTO");
			}
		}
		return !errors.isEmpty();
	}
	
	private void deserialize(HashMap<String, String> values, String date)  {
		if (!Date.isValidDate(date)) {
			this.errors.add("Invalid date");
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
		return sensorValues;
	}


}
