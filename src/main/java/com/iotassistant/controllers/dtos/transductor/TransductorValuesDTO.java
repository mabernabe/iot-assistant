package com.iotassistant.controllers.dtos.transductor;

import java.util.HashMap;

import com.iotassistant.models.transductor.Property;

public class TransductorValuesDTO {
	
	HashMap<Property, String> values;
	
	private String date;

	public TransductorValuesDTO(String date) {
		super();
		this.date = date;
	}

	public HashMap<Property, String> getValues() {
		return values;
	}

	public void setValues(HashMap<Property, String> values) {
		this.values = values;
	}

	public String getDate() {
		return date;
	}



}
