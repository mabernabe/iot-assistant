package com.iotassistant.models.transductor;

public abstract class TransductorValue {
	
	protected String value;
	
	protected Property property;
	
	protected String date;
	
	public String getValue() {
		return value;
	}

	public String getUnit() {
		return property.getUnit();
	}


	public String getDescriptiveInformationFromValue() {
		return property.getDescriptiveInformationFromValue(value);
	}


	public String getDate() {
		return date;
	}


}
