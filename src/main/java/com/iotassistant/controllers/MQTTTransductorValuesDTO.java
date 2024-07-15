package com.iotassistant.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.TransductorValues;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.utils.Date;

public abstract class MQTTTransductorValuesDTO<T extends Property> {
	
	private TransductorValues<T> transductorValues;
	
	private List<String> errors;
	
	@JsonCreator
	public MQTTTransductorValuesDTO(@JsonProperty(value = "values" , required = true) HashMap<String, String> values, @JsonProperty(value = "date", required = true) String date){
		super();
		this.errors = new ArrayList<String>();
		deserialize(values, date);
	}
	
	private void deserialize(HashMap<String, String> values, String date)  {
		if (!Date.isValidDate(date)) {
			this.getErrors().add("Invalid date");
		}
		this.transductorValues = this.createTransductorValues(date);
		for(String propertyString : values.keySet() ) {
			T property = this.getPropertyInstance(propertyString);
			if (property == null) {
				this.getErrors().add("Unknown property measured " + propertyString);
			} else {
				String value = values.get(propertyString);
				this.transductorValues.getValues().put(property, value);
				if (!property.isValidValue(value)) {
					this.getErrors().add("Value is invalid :" + value);	
				}
			}
		}		
	}

	protected abstract T getPropertyInstance(String propertyString);

	protected abstract TransductorValues<T> createTransductorValues(String date);

	protected List<String> getErrors() {
		return errors;
	}
	
	public boolean hasErrors(List<T> properties) {
		Set<T> dtoProperties = this.transductorValues.getValues().keySet();
		for (T property: properties) {
			if (!dtoProperties.contains(property)) {
				this.getErrors().add(property.getNameWithUnit() + " not found in DTO");
			}
		}
		return !this.getErrors().isEmpty();
	}

	protected TransductorValues<T> getTransductorValues() {
		return transductorValues;
	}
	
	
	
}