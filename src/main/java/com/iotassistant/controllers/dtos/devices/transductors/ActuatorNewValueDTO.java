package com.iotassistant.controllers.dtos.devices.transductors;

import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

public class ActuatorNewValueDTO {
	
	private String propertyActuated;
	
	private String value;

	public ActuatorNewValueDTO(String propertyActuated, String value) {
		super();
		this.propertyActuated = propertyActuated;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public PropertyActuatedEnum getPropertyActuated() {
		return PropertyActuatedEnum.getInstance(this.propertyActuated);
	}

	

}
