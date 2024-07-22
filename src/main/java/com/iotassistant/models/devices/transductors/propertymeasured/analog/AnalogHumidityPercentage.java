package com.iotassistant.models.devices.transductors.propertymeasured.analog;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredSeverity;

public class AnalogHumidityPercentage extends AnalogPropertyMeasured{
	
	private static final String NAME = "Humidity";
	
	private static final String UNIT = "%";
	
	private static final Integer MAXIMUM_VALUE = 100;
	
	private static final Integer MINIMUM_VALUE = 0;

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
		float humidity = Float.parseFloat(value);
		if(humidity < 30) { return "Too dry";}
        else if(30 < humidity  && humidity < 50) {return "Optimal";}
        else {return "Too humid";}
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
