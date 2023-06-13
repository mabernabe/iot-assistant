package com.iotassistant.models.transductor.propertymeasured.analog;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public class AnalogAmbientLightLux extends AnalogPropertyMeasured{
	
private static final String NAME = "Ambient Light";
	
	private static final String UNIT = "Lux";
	
	private static final Integer MAXIMUM_VALUE = 1500;
	
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
		float lux = Float.parseFloat(value);
		if(lux < 5) { return "Dark";}
        else if(lux < 50) {return "Cloudy indoor";}
        else if(lux < 500) {return "Light indoor";}
        else if(lux < 1000) {return "Sunny indoor";}
        else {return "Sunlight";}
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
