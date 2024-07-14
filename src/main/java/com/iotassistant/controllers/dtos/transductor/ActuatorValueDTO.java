package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class ActuatorValueDTO extends TransductorValueDTO{
	
	public ActuatorValueDTO(PropertyActuatedEnum propertyActuated, String string) {
		super(propertyActuated, string);

	}

}
