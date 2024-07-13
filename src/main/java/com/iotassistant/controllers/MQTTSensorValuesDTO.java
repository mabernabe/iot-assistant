package com.iotassistant.controllers;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.TransductorValues;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

//Sensor Values JSON Example : { "values" : {"TEMPERATURE_CENTIGRADES" : "25"}, "date" : "2022-03-23 15:41:03"} or  { "values" : {"Motion" : "true"}, "date" : "2022-03-23 15:41:03"}
public class MQTTSensorValuesDTO extends MQTTTransductorValuesDTO<PropertyMeasuredEnum>{
	
		
	@JsonCreator
	public MQTTSensorValuesDTO(@JsonProperty(value = "values" , required = true) HashMap<String, String> values, @JsonProperty(value = "date", required = true) String date){
		super(values, date);
	}
	
	@Override
	protected TransductorValues<PropertyMeasuredEnum> createTransductorValues(String date) {
		return new SensorValues(new HashMap<PropertyMeasuredEnum, String>(), date);
	}

	@Override
	protected PropertyMeasuredEnum getPropertyInstance(String propertyString) {
		return PropertyMeasuredEnum.getInstance(propertyString);
	}
	
	public SensorValues getSensorValues() {
		return (SensorValues) this.getTransductorValues();
	}

}
