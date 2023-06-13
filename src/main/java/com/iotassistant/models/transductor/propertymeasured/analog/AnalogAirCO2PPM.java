package com.iotassistant.models.transductor.propertymeasured.analog;

import java.util.HashMap;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogAirCO2PPM extends AnalogPropertyMeasured{
	
	private static final String NAME = "Air CO2";
	
	private static final String UNIT = "PPM";
	
	private static final Integer MAXIMUM_VALUE = 2000;
	
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
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_GOOD, "Low CO2");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_AVERAGE, "Average CO2");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD, "Little high CO2");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_BAD, "High CO2");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_WORSE, "Very high CO2");
		descriptionsMap.put(PropertyMeasuredSeverity.SEVERITY_VERY_BAD, "Extremely high CO2");
		return descriptionsMap.get(getSeverity(value));
	}

	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		float ppm = Float.parseFloat(value);
		if(ppm < 530) { return PropertyMeasuredSeverity.SEVERITY_GOOD;}
        else if(ppm < 850) {return PropertyMeasuredSeverity.SEVERITY_AVERAGE;}
        else if(ppm < 1250) {return PropertyMeasuredSeverity.SEVERITY_LITTLE_BAD;}
        else if(ppm < 1500) {return PropertyMeasuredSeverity.SEVERITY_BAD;}
        else if(ppm < 1700) { return PropertyMeasuredSeverity.SEVERITY_WORSE;}
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
