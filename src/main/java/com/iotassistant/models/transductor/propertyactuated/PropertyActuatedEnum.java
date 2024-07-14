package com.iotassistant.models.transductor.propertyactuated;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iotassistant.models.transductor.Property;

public enum PropertyActuatedEnum implements Property{
	DIGITAL_LED(new DigitalLED()),
	DIGITAL_RELAY(new DigitalRelay()),
	DIGITAL_SOCKET(new DigitalSocket()),
	ANALOG_SPEAKER_ID(new AnalogSpeakerId());
	
	public static final List<PropertyActuatedEnum> ALL_INSTANCES = Arrays.asList(PropertyActuatedEnum.values()); 

	private PropertyActuated propertyActuated;

	private PropertyActuatedEnum(PropertyActuated propertyActuated) {
		this.propertyActuated = propertyActuated;
	}

	@Override
	public String getName() {
		return propertyActuated.getName();
	}
	
	@Override
	public String getNameWithUnit() {
		return propertyActuated.getNameWithUnit();
	}

	public boolean isDigital() {
		return propertyActuated.isDigital();
	}

	public String getUnit() {
		return propertyActuated.getUnit();
	}


	@JsonCreator
	public static PropertyActuatedEnum getInstance(String string) {
		for (PropertyActuatedEnum propertyActuated : PropertyActuatedEnum.values()) { 
            if (propertyActuated.toString().equals(string) || propertyActuated.getNameWithUnit()!= null && propertyActuated.getNameWithUnit().equals(string)) {
            	return propertyActuated;
            }; 
        }
		return null;
	}


	public Integer getMaximumValue() {
		return propertyActuated.getMaximumValue();
	}

	public Integer getMinimumValue() {
		return propertyActuated.getMinimumValue();
	}

	public String getDescriptionFromValue(String value) {
		return propertyActuated.getDescriptiveInformationFromValue(value);
	}

}
