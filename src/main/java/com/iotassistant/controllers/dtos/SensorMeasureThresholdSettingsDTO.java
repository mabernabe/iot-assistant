package com.iotassistant.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.sensorrules.SensorMeasureThresholdSettings;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class SensorMeasureThresholdSettingsDTO {
	
	protected String sensorName;
	
	protected PropertyDTO sensorProperty;
	
	protected String sensorValueThreshold;
	
	protected String sensorAnalogThresholdOperator;

	public SensorMeasureThresholdSettingsDTO(String sensorName, PropertyDTO sensorProperty, String sensorValueThreshold,
			String sensorAnalogThresholdOperator) {
		super();
		this.sensorName = sensorName;
		this.sensorProperty = sensorProperty;
		this.sensorValueThreshold = sensorValueThreshold;
		this.sensorAnalogThresholdOperator = sensorAnalogThresholdOperator;
	}
	
	public SensorMeasureThresholdSettingsDTO(SensorMeasureThresholdSettings sensorMeasureThresholdSettings) {
		this.sensorName = sensorMeasureThresholdSettings.getSensorName();
		this.sensorProperty = new PropertyDTO(sensorMeasureThresholdSettings.getPropertyObserved());
		this.sensorValueThreshold = sensorMeasureThresholdSettings.getValueThresholdObserved();
		this.sensorAnalogThresholdOperator = (sensorMeasureThresholdSettings.getAnalogThresholdOperator() == null) ? null : sensorMeasureThresholdSettings.getAnalogThresholdOperator().toString();
	}

	@JsonIgnore
	public SensorMeasureThresholdSettings getSensorMeasureThresholdSettings() {
		PropertyMeasuredEnum sensorProperty = PropertyMeasuredEnum.getInstance(this.getSensorProperty().getName());
		AnalogThresholdOperatorEnum analogThresholdOperator = (sensorProperty.isBinary())? null : AnalogThresholdOperatorEnum.getInstance(this.getSensorAnalogThresholdOperator());
		return new SensorMeasureThresholdSettings(sensorName, sensorProperty, analogThresholdOperator, sensorValueThreshold);
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public PropertyDTO getSensorProperty() {
		return sensorProperty;
	}

	public void setSensorProperty(PropertyDTO sensorProperty) {
		this.sensorProperty = sensorProperty;
	}

	public String getSensorValueThreshold() {
		return sensorValueThreshold;
	}

	public void setSensorValueThreshold(String sensorValueThreshold) {
		this.sensorValueThreshold = sensorValueThreshold;
	}

	public String getSensorAnalogThresholdOperator() {
		return sensorAnalogThresholdOperator;
	}

	public void setSensorAnalogThresholdOperator(String sensorAnalogThresholdOperator) {
		this.sensorAnalogThresholdOperator = sensorAnalogThresholdOperator;
	}
	
	

}
