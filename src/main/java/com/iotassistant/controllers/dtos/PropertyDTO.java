package com.iotassistant.controllers.dtos;

import com.iotassistant.models.transductor.Property;

public class PropertyDTO {
		
	private String name;
	
	private String unit;
	
	private boolean isBinary;
	
	private Integer maximumValue;
	
	private Integer minimumValue;
	
	public PropertyDTO() {
		super();
	}

	public PropertyDTO(Property property) {
		this.name = property.getName();
		this.unit = property.getUnit();
		this.isBinary = property.isBinary();
		this.maximumValue = property.getMaximumValue();
		this.minimumValue = property.getMinimumValue();
	}

	public String getName() {
		return name;
	}


	public String getUnit() {
		return unit;
	}

	public boolean isBinary() {
		return isBinary;
	}


	public Integer getMaximumValue() {
		return maximumValue;
	}

	public Integer getMinimumValue() {
		return minimumValue;
	}
	
	
	
}
