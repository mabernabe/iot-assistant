package com.iotassistant.controllers.dtos.sensorrules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.sensorrules.CameraSensorRule;
import com.iotassistant.models.sensorrules.SensorMeasureThresholdSettings;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;

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
		NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.notificationType);
		SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
		return new CameraSensorRule(sensorMeasureThresholdSettings, cameraName, notificationType, timeBetweenTriggers, enabled);
	}
	


}
