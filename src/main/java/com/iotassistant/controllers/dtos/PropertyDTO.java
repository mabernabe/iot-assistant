package com.iotassistant.controllers.dtos;

import com.iotassistant.models.transductor.Property;

public class PropertyDTO {
		
	private String name;
	
	private String shortName;
	
	boolean isDigital;
	
	private Integer maximumValue;
	
	private Integer minimumValue;
	
	public PropertyDTO() {
		super();
	}

	public PropertyDTO(Property property) {
		this.name = property.toStringWithUnit();
		this.shortName = property.toString();
		this.isDigital = property.isDigital();
		this.maximumValue = property.getMaximumValue();
		this.minimumValue = property.getMinimumValue();
	}

	public String getName() {
		return name;
	}


	public String getShortName() {
		return shortName;
	}

	public boolean isDigital() {
		return isDigital;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDigital(boolean isDigital) {
		this.isDigital = isDigital;
	}

	public Integer getMaximumValue() {
		return maximumValue;
	}

	public Integer getMinimumValue() {
		return minimumValue;
	}
	
	
	
}
