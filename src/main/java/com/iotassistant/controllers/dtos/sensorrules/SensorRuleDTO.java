package com.iotassistant.controllers.dtos.sensorrules;

import com.iotassistant.controllers.dtos.SensorMeasureThresholdSettingsDTO;
import com.iotassistant.models.sensorrules.SensorRule;

public class SensorRuleDTO {
	
	SensorMeasureThresholdSettingsDTO sensorMeasureThresholdSettings;
	
	protected int id;
	
	protected String type;
	
	protected boolean enabled;
	
	protected String timeBetweenTriggers;
	
	protected String notificationType;

	public SensorRuleDTO() {
		super();
	}



	public SensorRuleDTO(SensorRule sensorRule) {
		this.id = sensorRule.getId();
		this.type = sensorRule.getType().getString();
		this.enabled = sensorRule.isEnabled();
		this.sensorMeasureThresholdSettings = new SensorMeasureThresholdSettingsDTO(sensorRule.getSensorMeasureThresholdSettings());
		this.timeBetweenTriggers = sensorRule.getTimeBetweenTriggers().toString();
		this.notificationType = sensorRule.getNotificationType().toString();
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getTimeBetweenTriggers() {
		return timeBetweenTriggers;
	}


	public void setTimeBetweenTriggers(String timeBetweenTriggers) {
		this.timeBetweenTriggers = timeBetweenTriggers;
	}



	public String getNotificationType() {
		return notificationType;
	}



	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}



	public SensorMeasureThresholdSettingsDTO getSensorMeasureThresholdSettings() {
		return sensorMeasureThresholdSettings;
	}



	public void setSensorMeasureThresholdSettings(SensorMeasureThresholdSettingsDTO sensorMeasureThresholdSettings) {
		this.sensorMeasureThresholdSettings = sensorMeasureThresholdSettings;
	}

	

}
