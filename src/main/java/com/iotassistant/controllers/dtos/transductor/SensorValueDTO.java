package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

class SensorValueDTO extends TransductorValueDTO{
	
	private int severity;
	
	SensorValueDTO(PropertyMeasuredEnum propertyMeasured, String string) {
		super(propertyMeasured, string);
		this.severity = propertyMeasured.getSeverity(string);
	}

	public int getSeverity() {
		return severity;
	}
}
