package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogAirQualityIAQ extends AnalogPropertyMeasured{
	
	private static final String NAME = "Air Quality";
	
	private static final String UNIT = "IAQ";
	
	private static final Integer MAXIMUM_VALUE = 500;
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Good air");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Average air");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Little bad air");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "Bad air");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Worse air");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Very bad air");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float iaq = Float.parseFloat(value);
		if(iaq < 50) { return PropertyMeasuredSeverity.SEVERITY_GOOD;}
        else if(iaq < 100) {return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
        else if(iaq < 150) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(iaq < 200) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(iaq < 300) { return PropertyMeasuredSeverity.SEVERITY_WORSE;}
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
