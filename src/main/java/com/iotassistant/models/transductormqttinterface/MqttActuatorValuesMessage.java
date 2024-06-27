package com.iotassistant.models.transductormqttinterface;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class MqttActuatorValuesMessage {

	private HashMap<PropertyActuatedEnum, String> values;

	String date;
	
	public MqttActuatorValuesMessage() {
		super();
	}




	public void setValues(HashMap<PropertyActuatedEnum, String> values) {
		this.values = values;
	}


	public String getValue(PropertyActuatedEnum propertyActuated) {
		return values.get(propertyActuated);
	}


	public String getDate() {
		return date;
	}

	

}
