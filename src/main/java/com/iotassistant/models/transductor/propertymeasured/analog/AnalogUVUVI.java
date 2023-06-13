package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogUVUVI extends AnalogPropertyMeasured{
	
	private static final String NAME = "UV";
	
	private static final String UNIT = "UVI";
	
	private static final Integer MAXIMUM_VALUE = 12;
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Low UV");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Average UV");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Little high UV");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "High UV");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Very high UV");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Extremely high UV");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float uvi = Float.parseFloat(value);
		if(uvi < 46) { return PropertyMeasuredSeverity.SEVERITY_GOOD;}
        else if(uvi < 83) {return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
        else if(uvi < 142) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(uvi < 180) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(uvi < 221) { return PropertyMeasuredSeverity.SEVERITY_WORSE;}
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
