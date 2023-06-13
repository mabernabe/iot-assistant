package com.iotassistant.models.transductor.propertymeasured.analog;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogEncoder60U extends AnalogPropertyMeasured{
	
	private static final String NAME = "Encoder";
	
	private static final String UNIT = "60U";
	
	private static final Integer MAXIMUM_VALUE = 360;
	
	private static final Integer MINIMUM_VALUE = -360;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getUnit() {
		return UNIT;
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
		return MAXIMUM_VALUE;
	}

	@Override
	public Integer getMinimumValue() {
		return MINIMUM_VALUE;
	}

}
