package com.iotassistant.models.transductor;

import com.iotassistant.utils.Date;

public class SensorMeasureValueEvent {

	String value;
	
	String date;
	
	public SensorMeasureValueEvent(String value) {
		super();
		this.value = value; 
		this.date = Date.getCurrentDate();
	}


	public SensorMeasureValueEvent(String value, String date) {
		super();
		this.value = value; 
		this.date = date;
	}

	public String getValue() {
		return value;
	}


	public String getDate() {
		return date;
	}


	
	
}
