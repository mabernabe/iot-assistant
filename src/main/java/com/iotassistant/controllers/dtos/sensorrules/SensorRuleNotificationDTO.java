package com.iotassistant.controllers.dtos.sensorrules;

import com.iotassistant.controllers.dtos.NotificationDTO;
import com.iotassistant.models.notifications.SensorRuleNotification;

public class SensorRuleNotificationDTO extends NotificationDTO{
	
	private String sensorValue;

	public SensorRuleNotificationDTO(SensorRuleNotification sensorRuleNotification) {
		super(sensorRuleNotification.getId(), sensorRuleNotification.getDate());
		this.sensorValue = sensorRuleNotification.getSensorValue();
	}

	public String getSensorValue() {
		return sensorValue;
	}
	
	
	
	

}
