package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

class ActuatorValueDTO extends TransductorValueDTO{
	
	ActuatorValueDTO(PropertyActuatedEnum propertyActuated, String string) {
		super(propertyActuated, string);

	}

}
