package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.ActuatorValues;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class ActuatorValueDTO extends TransductorValueDTO{
	
	public ActuatorValueDTO() {};
	
	public ActuatorValueDTO(ActuatorValues actuatorValue) {
		super();

	}

	public String getPropertyActuated() {
		return property;
	}

	public void setPropertyActuated(PropertyActuatedEnum propertyActuated) {
		this.property = propertyActuated.toStringWithUnit();
	}

	public void setValue(String value) {
		this.value = value;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}
			

}
