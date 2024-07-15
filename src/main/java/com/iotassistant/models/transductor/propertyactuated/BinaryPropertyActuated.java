package com.iotassistant.models.transductor.propertyactuated;

public abstract class BinaryPropertyActuated  implements PropertyActuated{
	
	private static final String BINARY_HIGH_STRING = "High";

	private static final String BINARY_LOW_STRING = "Low";
	
	@Override
	public boolean isBinary() {
		return true;
	}
	
	@Override
	public String getUnit() {
		return null;
	}
	
	@Override
	public String getDescriptiveInformationFromValue(String value) {
		return null;
	}
	
	
	@Override
	public Integer getMaximumValue() {
		return null;
	}

	@Override
	public Integer getMinimumValue() {
		return null;
	}
	
	public static String getBinaryValueString(boolean isHigh) {
		if (isHigh) {
			return BINARY_HIGH_STRING;
		}
		else {
			return BINARY_LOW_STRING;
		}
	}

	public static Boolean getBinaryValueFromString(String value) {
		if (value.equalsIgnoreCase(BINARY_HIGH_STRING)) {
			return true;
		}
		else if (value.equalsIgnoreCase(BINARY_LOW_STRING)){
			return false;
		}
		else return null; 
	}
	
	@Override
	public String getNameWithUnit() {
		return this.getName();
	}

}
