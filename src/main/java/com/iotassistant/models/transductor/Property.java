package com.iotassistant.models.transductor;


public interface Property {
	
	String toString();
	
	String toStringWithUnit();
	
	boolean isDigital();
	
	String getUnit();

	Integer getMaximumValue();

	Integer getMinimumValue();

	String getDescriptiveInformationFromValue(String value);

}
