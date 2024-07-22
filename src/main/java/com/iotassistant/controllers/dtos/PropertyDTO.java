package com.iotassistant.controllers.dtos;

import com.iotassistant.models.transductor.Property;

public class PropertyDTO {
		
	private String name;
	
	private String nameWithUnit;
	
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
		this.nameWithUnit = property.getNameWithUnit();
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
	
	
	public String getNameWithUnit() {
		return nameWithUnit;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameWithUnit(String nameWithUnit) {
		this.nameWithUnit = nameWithUnit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setBinary(boolean isBinary) {
		this.isBinary = isBinary;
	}

	public void setMaximumValue(Integer maximumValue) {
		this.maximumValue = maximumValue;
	}

	public void setMinimumValue(Integer minimumValue) {
		this.minimumValue = minimumValue;
	}
	
	
	
}
