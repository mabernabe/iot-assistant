package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogAirCOPPM extends AnalogPropertyMeasured{
	
	private static final String NAME = "Air CO";
	
	private static final String UNIT = "PPM";
	
	private static final Integer MAXIMUM_VALUE = 1000;
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Low CO");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Average CO");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Little high CO");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "High CO");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Very high CO");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Extremely high CO");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float ppm = Float.parseFloat(value);
		if(ppm < 40) { return PropertyMeasuredSeverity.SEVERITY_GOOD;}
        else if(ppm < 50) {return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
        else if(ppm < 60) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(ppm < 80) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(ppm < 90) { return PropertyMeasuredSeverity.SEVERITY_WORSE;}
        else {return PropertyMeasuredSeverity.SEVERITY_VERY_BAD;}
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
