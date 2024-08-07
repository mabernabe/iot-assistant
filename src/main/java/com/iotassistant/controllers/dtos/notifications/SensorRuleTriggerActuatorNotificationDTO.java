package com.iotassistant.controllers.dtos.notifications;

import com.iotassistant.controllers.dtos.TriggerActuatorSensorRuleDTO;
import com.iotassistant.models.notifications.SensorRuleTriggerActuatorNotification;

public class SensorRuleTriggerActuatorNotificationDTO extends SensorRuleNotificationDTO{
	
	private TriggerActuatorSensorRuleDTO triggerActuatorSensorRule;
	

	public SensorRuleTriggerActuatorNotificationDTO(
			SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification) {
		super(sensorRuleTriggerActuatorNotification);
		this.triggerActuatorSensorRule = new TriggerActuatorSensorRuleDTO(sensorRuleTriggerActuatorNotification.getSensorRule());
	}

	

	public TriggerActuatorSensorRuleDTO getTriggerActuatorSensorRule() {
		return triggerActuatorSensorRule;
	}

	

}
