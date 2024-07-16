package com.iotassistant.controllers;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class MQTTSetActuatorValueDTO {
	
private PropertyActuatedEnum propertyActuated;
	
	private String value;

	public MQTTSetActuatorValueDTO(PropertyActuatedEnum propertyActuated, String value) {
		super();
		this.propertyActuated = propertyActuated;
		this.value = value;
	}

	public String getPropertyActuated() {
		return propertyActuated.getNameWithUnit();
	}

	public void setPropertyActuated(PropertyActuatedEnum propertyActuated) {
		this.propertyActuated = propertyActuated;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
