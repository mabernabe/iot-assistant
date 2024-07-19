package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.Property;

abstract class TransductorValueDTO {
	
	private String string;
	
	private String unit;
	
	private String description;

	TransductorValueDTO(Property property, String string) {
		super();
		this.string = string;
		this.description = property.getDescriptionFromValue(string);
		this.unit = property.getUnit();
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

}
