package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class SensorValueDTO {
	
	private String string;
	
	private String unit;
	
	private String description;
	
	private int severity;
	
	public SensorValueDTO(PropertyMeasuredEnum propertyMeasured, String string) {
		this.string = string;
		this.description = propertyMeasured.getDescriptionFromValue(string);
		this.severity = propertyMeasured.getSeverity(string);
		this.unit = propertyMeasured.getUnit();
	}

	public String getString() {
		return string;
	}

	public String getUnit() {
		return unit;
	}


	public String getDescription() {
		return description;
	}

	public int getSeverity() {
		return severity;
	}
	
	

}
