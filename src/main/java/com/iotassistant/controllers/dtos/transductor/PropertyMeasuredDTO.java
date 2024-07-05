package com.iotassistant.controllers.dtos.transductor;
import com.iotassistant.controllers.dtos.PropertyDTO;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class PropertyMeasuredDTO extends PropertyDTO{

	public PropertyMeasuredDTO(PropertyMeasuredEnum propertyMeasured) {
		super(propertyMeasured);
	}
	
	

}
