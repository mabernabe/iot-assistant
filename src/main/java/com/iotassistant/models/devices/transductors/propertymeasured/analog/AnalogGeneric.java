package com.iotassistant.models.devices.transductors.propertymeasured.analog;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredSeverity;

public class AnalogGeneric extends AnalogPropertyMeasured{

	private static final String NAME = "Generic Analog";
	
	private static final String UNIT = "NA";
	
	private static final Integer MAXIMUM_VALUE = 50000;
	
	private static final Integer MINIMUM_VALUE = -50000;
	
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
