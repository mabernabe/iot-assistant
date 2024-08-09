package com.iotassistant.models.devices.transductors.propertymeasured.analog;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredSeverity;

public class AnalogVoiceId extends AnalogPropertyMeasured{

	private static final String NAME = "Voice";
	
	private static final String UNIT = "Id";
	
	private static final Integer MINIMUM_VALUE = 0;
	
	private static final Integer MAXIMUM_VALUE = 1000;
	
	
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
		int gestureIndex = Integer.valueOf(value);
		switch (gestureIndex) {
		case 0:
			return "No Command";
        case 1:
        	return "Apagar la lampara";
        default:
        	return "Unrecognized";
		}
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		int gestureIndex = Integer.valueOf(value);
		if (gestureIndex == 0) {
			return PropertyMeasuredSeverity.SEVERITY_VERY_BAD;
		}
		else {
			return PropertyMeasuredSeverity.SEVERITY_GOOD;
		}
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

