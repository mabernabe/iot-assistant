package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogHeartRatePPM extends AnalogPropertyMeasured{
	
	private static final String NAME = "Heart Rate";
	
	private static final String UNIT = "PPM";
	
	private static final Integer MAXIMUM_VALUE = 220;
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Athlete");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Excellent");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Average");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "Below average");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Effort");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Intense");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float ppm = Float.parseFloat(value);
		if(ppm > 150) { return PropertyMeasuredSeverity.SEVERITY_VERY_BAD;}
        else if(ppm > 90) {return PropertyMeasuredSeverity.SEVERITY_WORSE;}
        else if(ppm > 80) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(ppm > 70) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(ppm > 60) { return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
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
