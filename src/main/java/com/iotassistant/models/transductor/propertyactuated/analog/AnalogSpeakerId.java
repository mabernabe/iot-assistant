package com.iotassistant.models.transductor.propertyactuated.analog;

public class AnalogSpeakerId extends AnalogPropertyActuated{
	
	private static final String NAME = "Speaker";
	
	private static final String UNIT = "Id";
	
	private static final Integer MINIMUM_VALUE = 0;
	
	private static final Integer MAXIMUM_VALUE = 10;

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
		// TODO Auto-generated method stub
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
