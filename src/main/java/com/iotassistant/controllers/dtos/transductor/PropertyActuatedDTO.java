package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.controllers.dtos.PropertyDTO;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

class PropertyActuatedDTO extends PropertyDTO{

	PropertyActuatedDTO(PropertyActuatedEnum propertyActuatedEnum) {
		super(propertyActuatedEnum);
	}
	
	

}
