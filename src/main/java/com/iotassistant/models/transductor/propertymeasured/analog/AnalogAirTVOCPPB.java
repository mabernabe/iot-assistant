package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogAirTVOCPPB extends AnalogPropertyMeasured{
	
	private static final String NAME = "Air TVOC";
	
	private static final String UNIT = "PPB";
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Low TVOC");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Average TVOC");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Little high TVOC");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "High TVOC");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Very high TVOC");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Extremely high TVOC");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float ppb = Float.parseFloat(value);
		if(ppb < 55) { return PropertyMeasuredSeverity.SEVERITY_GOOD;}
        else if(ppb < 120) {return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
        else if(ppb < 220) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(ppb < 500) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(ppb < 800) { return PropertyMeasuredSeverity.SEVERITY_WORSE;}
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
