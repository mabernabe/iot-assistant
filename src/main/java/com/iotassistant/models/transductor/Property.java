package com.iotassistant.models.transductor;


public interface Property {
	
	String getName();
	
	String getNameWithUnit();
	
	boolean isBinary();
	
	String getUnit();

	Integer getMaximumValue();

	Integer getMinimumValue();

	String getDescriptionFromValue(String value);

	public default boolean isValidValue(String string) {
		if (this.isBinary()) {
			return string != null && string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
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
