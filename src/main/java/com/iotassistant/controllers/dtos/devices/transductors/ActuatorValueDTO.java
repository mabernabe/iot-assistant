package com.iotassistant.controllers.dtos.devices.transductors;

import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

class ActuatorValueDTO extends TransductorValueDTO{
	
	ActuatorValueDTO(PropertyActuatedEnum propertyActuated, String string) {
		super(propertyActuated, string);

	}

}
