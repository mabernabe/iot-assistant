package com.iotassistant.models.transductormqttinterface;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class MqttSensorNotificationMessage {
	
	private PropertyMeasuredEnum propertyMeasured;
	
	private String valueThreshold;
	
	private String value;
	
	private String date;

	

	public MqttSensorNotificationMessage(PropertyMeasuredEnum propertyMeasured, String valueThreshold, String value,
			String date) {
		super();
		this.propertyMeasured = propertyMeasured;
		this.valueThreshold = valueThreshold;
		this.value = value;
		this.date = date;
	}

	public MqttSensorNotificationMessage() {
		super();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PropertyMeasuredEnum getPropertyMeasured() {
		return propertyMeasured;
	}

	public void setPropertyMeasured(PropertyMeasuredEnum propertyMeasured) {
		this.propertyMeasured = propertyMeasured;
	}

	public String getValueThreshold() {
		return valueThreshold;
	}

	public void setValueThreshold(String valueThreshold) {
		this.valueThreshold = valueThreshold;
	}
	
	

}
