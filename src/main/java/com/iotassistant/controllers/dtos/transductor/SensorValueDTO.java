package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class SensorValueDTO extends TransductorValueDTO{
	
	private int severity;
	
	public SensorValueDTO(PropertyMeasuredEnum propertyMeasured, String string) {
		super(propertyMeasured, string);
		this.severity = propertyMeasured.getSeverity(string);
	}

	public int getSeverity() {
		return severity;
	}
}
