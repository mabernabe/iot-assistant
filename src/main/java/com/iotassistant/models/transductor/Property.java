package com.iotassistant.models.transductor;


public interface Property {
	
	String getName();
	
	String getNameWithUnit();
	
	boolean isBinary();
	
	String getUnit();

	Integer getMaximumValue();

	Integer getMinimumValue();

	String getDescriptionFromValue(String value);

	public default boolean isValidValue(String value) {
		if (this.isBinary()) {
			return value != null && value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
		} else {
			try {
				Float.parseFloat(value);
			} catch(  NullPointerException | NumberFormatException e) {
				return false;
			}		
		}
		return true;
	}

}
