package com.iotassistant.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.AlarmSensorRule;
import com.iotassistant.models.SensorMeasureThresholdSettings;
import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.notifications.NotificationTypeEnum;

public class AlarmSensorRuleDTO extends SensorRuleDTO{

	public AlarmSensorRuleDTO() {
		super();
	}

	public AlarmSensorRuleDTO(AlarmSensorRule alarmSensorRule) {
		super(alarmSensorRule);
	}

	@JsonIgnore
	public AlarmSensorRule getSensorRule() {
			SensorRuleTriggerIntervalEnum timeBetweenTriggers = SensorRuleTriggerIntervalEnum.getInstance(this.getTimeBetweenTriggers());
			NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.getNotificationType());
			SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
			return new AlarmSensorRule(sensorMeasureThresholdSettings, notificationType, timeBetweenTriggers, this.isEnabled());
	}


	
	

}
