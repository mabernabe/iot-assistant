package com.iotassistant.models.transductor;


public interface Property {
	
	String getName();
	
	String getNameWithUnit();
	
	boolean isDigital();
	
	String getUnit();

	Integer getMaximumValue();

	Integer getMinimumValue();

	String getDescriptionFromValue(String value);

}
