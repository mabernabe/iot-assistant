package com.iotassistant.controllers.dtos.transductor;
import com.iotassistant.controllers.dtos.PropertyDTO;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

class PropertyMeasuredDTO extends PropertyDTO{

	PropertyMeasuredDTO(PropertyMeasuredEnum propertyMeasured) {
		super(propertyMeasured);
	}
	
	

}
