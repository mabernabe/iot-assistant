package com.iotassistant.models.transductor.propertyactuated;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.propertyactuated.digital.DigitalLED;
import com.iotassistant.models.transductor.propertyactuated.digital.DigitalRelay;
import com.iotassistant.models.transductor.propertyactuated.digital.DigitalSocket;
import com.iotassistant.models.transductor.propertyactuated.analog.AnalogSpeakerId;

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
	public String toString() {
		return propertyActuated.getName();
	}


	public boolean isDigital() {
		return propertyActuated.isDigital();
	}

	public String getUnit() {
		return propertyActuated.getUnit();
	}


	@JsonCreator
	public static PropertyActuatedEnum getInstance(String string) {
		PropertyActuatedEnum propertyActuatedReturned= null;
		for (PropertyActuatedEnum propertyActuated : PropertyActuatedEnum.values()) { 
            if (propertyActuated.toStringWithUnit()!= null && propertyActuated.toStringWithUnit().equals(string)) {
            	return propertyActuatedReturned = propertyActuated;
            }; 
        }
		return propertyActuatedReturned;
	}

	@Override
	public String toStringWithUnit() {
		return propertyActuated.toStringWithUnit();
	}

	public Integer getMaximumValue() {
		return propertyActuated.getMaximumValue();
	}

	public Integer getMinimumValue() {
		return propertyActuated.getMinimumValue();
	}

	public String getDescriptiveInformationFromValue(String value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
