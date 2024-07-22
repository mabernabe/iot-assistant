package com.iotassistant.controllers.dtos.devices.transductors;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;

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
