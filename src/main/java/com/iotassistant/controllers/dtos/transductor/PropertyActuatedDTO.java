package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.controllers.dtos.PropertyDTO;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class PropertyActuatedDTO extends PropertyDTO{

	public PropertyActuatedDTO(PropertyActuatedEnum propertyActuatedEnum) {
		super(propertyActuatedEnum);
	}
	
	

}
