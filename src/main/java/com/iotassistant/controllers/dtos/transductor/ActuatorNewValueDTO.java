package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

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
