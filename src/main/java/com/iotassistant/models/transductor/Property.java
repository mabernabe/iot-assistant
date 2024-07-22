package com.iotassistant.models.transductor;


public interface Property {
	
	public static final String BINARY_PROPERTY_TRUE_STRING_VALUE = "true";
	
	public static final String BINARY_PROPERTY_FALSE_STRING_VALUE = "false";
	
	String getName();
	
	String getNameWithUnit();
	
	boolean isBinary();
	
	String getUnit();

	Integer getMaximumValue();

	Integer getMinimumValue();

	String getDescriptionFromValue(String value);

	public default boolean isValidValue(String string) {
		if (this.isBinary()) {
			return string != null && string.equalsIgnoreCase(BINARY_PROPERTY_TRUE_STRING_VALUE) 
					|| string.equalsIgnoreCase(BINARY_PROPERTY_FALSE_STRING_VALUE);
		} else {
			try {
				float number = Float.parseFloat(string);
				return this.getMinimumValue() <= number && number <= this.getMaximumValue();
			} catch(  NullPointerException | NumberFormatException e) {
				return false;
			}		
		}
	}

}
