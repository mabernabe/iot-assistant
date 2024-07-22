package com.iotassistant.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.CameraSensorRule;
import com.iotassistant.models.SensorMeasureThresholdSettings;
import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.notifications.NotificationTypeEnum;

public class CameraSensorRuleDTO extends SensorRuleDTO{
	
	private String cameraName;
	

	public CameraSensorRuleDTO() {
		super();
	}

	public CameraSensorRuleDTO(CameraSensorRule cameraSensorRule) {
		super(cameraSensorRule);
		this.cameraName = cameraSensorRule.getCameraName();
	}

	public String getCameraName() {
		return cameraName;
	}
	
	@JsonIgnore
	public CameraSensorRule getSensorRule() {
		SensorRuleTriggerIntervalEnum timeBetweenTriggers = SensorRuleTriggerIntervalEnum.getInstance(this.getTimeBetweenTriggers());		
		NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.getNotificationType());
		SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
		return new CameraSensorRule(sensorMeasureThresholdSettings, cameraName, notificationType, timeBetweenTriggers, this.isEnabled());
	}
	


}
