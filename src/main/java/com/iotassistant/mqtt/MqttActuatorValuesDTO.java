package com.iotassistant.mqtt;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.devices.transductors.ActuatorValues;
import com.iotassistant.models.devices.transductors.TransductorValues;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

class MqttActuatorValuesDTO extends MqttTransductorValuesDTO<PropertyActuatedEnum>{
	
		
	@JsonCreator
	public MqttActuatorValuesDTO(@JsonProperty(value = "values" , required = true) HashMap<String, String> values, @JsonProperty(value = "date", required = true) String date){
		super(values, date);
	}
	
	@Override
	protected TransductorValues<PropertyActuatedEnum> createTransductorValues(String date) {
		return new ActuatorValues(new HashMap<PropertyActuatedEnum, String>(), date);
	}

	@Override
	protected PropertyActuatedEnum getPropertyInstance(String propertyString) {
		return PropertyActuatedEnum.getInstance(propertyString);
	}
	
	public ActuatorValues getSensorValues() {
		return (ActuatorValues) this.getTransductorValues();
	}

}