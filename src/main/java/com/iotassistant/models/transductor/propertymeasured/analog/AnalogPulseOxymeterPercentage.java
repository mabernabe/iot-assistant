package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogPulseOxymeterPercentage extends AnalogPropertyMeasured{
	
	private static final String NAME = "Pulse Oxymeter";
	
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
		HashMap<PropertyMeasuredSeverity, String> descriptionsMap = new HashMap<>();
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Good");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "Bad");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float percentage = Float.parseFloat(value);
		if(percentage < 95) { return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else {return PropertyMeasuredSeverity.SEVERITY_GOOD;}
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
