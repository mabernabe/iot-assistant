package com.iotassistant.controllers.dtos.sensorrules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.sensorrules.AlarmSensorRule;
import com.iotassistant.models.sensorrules.SensorMeasureThresholdSettings;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;

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
			NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.notificationType);
			SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
			return new AlarmSensorRule(sensorMeasureThresholdSettings, notificationType, timeBetweenTriggers, enabled);
	}


	
	

}
