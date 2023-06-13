package com.iotassistant.models.transductor.propertymeasured.digital;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasured;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public abstract class DigitalPropertyMeasured implements PropertyMeasured{
	
	private static final String DIGITAL_HIGH_STRING = "High";

	private static final String DIGITAL_LOW_STRING = "Low";
	
	@Override
	public boolean isDigital() {
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
	public PropertyMeasuredSeverity getSeverity(String value) {
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
	
	public static String getDigitalValueString(boolean isHigh) {
		if (isHigh) {
			return DIGITAL_HIGH_STRING;
		}
		else {
			return DIGITAL_LOW_STRING;
		}
	}

	public static Boolean getDigitalValueFromString(String value) {
		if (value.equalsIgnoreCase(DIGITAL_HIGH_STRING)) {
			return true;
		}
		else if (value.equalsIgnoreCase(DIGITAL_LOW_STRING)){
			return false;
		}
		else return null; 
	}

	@Override
	public String toStringWithUnit() {
		return this.getName();
	}
	
}
