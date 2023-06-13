package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.transductor.ActuatorValue;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class MqttActuatorValuesMessage {

	private HashMap<PropertyActuatedEnum, String> values;

	String date;
	
	public MqttActuatorValuesMessage() {
		super();
	}


	public List<ActuatorValue> getValues() {
		List<ActuatorValue> values = new ArrayList<>();
		for (PropertyActuatedEnum propertyActuated : this.values.keySet()) {
			String value = this.values.get(propertyActuated);
			values.add(new ActuatorValue(value, propertyActuated, date));
		}
		return values;
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
