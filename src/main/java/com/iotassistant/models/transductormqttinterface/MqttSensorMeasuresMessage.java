package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.transductor.SensorMeasure;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.utils.Date;

//Deserialize JSON Example : { "measures" : {"Temperature C" : "25"}, "date" : "2022-03-23 15:41:03"}
public class MqttSensorMeasuresMessage {
	
	private HashMap<PropertyMeasuredEnum, String> measures;

	private String date;
		
	@JsonCreator
	public MqttSensorMeasuresMessage(@JsonProperty(value = "measures" , required = true) HashMap<PropertyMeasuredEnum, String> measures, @JsonProperty(value = "date", required = true) String date) throws IllegalArgumentException{
		super();
		this.measures = measures;
		this.date = date;
		if (!Date.isValidDate(date)) {
			throw new IllegalArgumentException();
		}
	}


	public List<SensorMeasure> getMeasures() {
		List<SensorMeasure> measures = new ArrayList<SensorMeasure>();
		for (PropertyMeasuredEnum propertyMeasured : this.measures.keySet()) {
			String measureValue = this.measures.get(propertyMeasured);
			measures.add(new SensorMeasure(measureValue, propertyMeasured, date));
		}
		return measures;
	}

	
	public String getDate() {
		return date;
	}



}
