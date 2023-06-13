package com.iotassistant.controllers.dtos.sensorrules;

import com.iotassistant.models.notifications.SensorRuleAlarmNotification;

public class SensorAlarmNotificationDTO extends SensorRuleNotificationDTO{
	
	private AlarmSensorRuleDTO alarmSensorRule;
	
	public SensorAlarmNotificationDTO(SensorRuleAlarmNotification alarmNotification) {
		super(alarmNotification);
		this.alarmSensorRule = new AlarmSensorRuleDTO(alarmNotification.getAlarmSensorRule());
	}

	

	public AlarmSensorRuleDTO getAlarmSensorRule() {
		return alarmSensorRule;
	}

}
