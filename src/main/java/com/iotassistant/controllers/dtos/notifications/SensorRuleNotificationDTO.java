package com.iotassistant.controllers.dtos.notifications;

import com.iotassistant.models.notifications.SensorRuleNotification;

public class SensorRuleNotificationDTO extends NotificationDTO{
	
	private String sensorValue;

	SensorRuleNotificationDTO(SensorRuleNotification sensorRuleNotification) {
		super(sensorRuleNotification.getId(), sensorRuleNotification.getDate());
		this.sensorValue = sensorRuleNotification.getSensorValue();
	}

	public String getSensorValue() {
		return sensorValue;
	}
	
	
	
	

}
