package com.iotassistant.models.devices.transductors.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredSeverity;

public class AnalogSoilMoistureRH extends AnalogPropertyMeasured{
	
	private static final String NAME = "Soil Moisture";
	
	private static final String UNIT = "%RH";
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Very wet");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Wet");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Average wet");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "Little Dry");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Dry");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Very Dry");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float ppm = Float.parseFloat(value);
		if(ppm < 15) { return PropertyMeasuredSeverity.SEVERITY_VERY_BAD;}
        else if(ppm < 30) {return PropertyMeasuredSeverity.SEVERITY_WORSE;}
        else if(ppm < 45) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(ppm < 60) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(ppm < 75) { return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
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
