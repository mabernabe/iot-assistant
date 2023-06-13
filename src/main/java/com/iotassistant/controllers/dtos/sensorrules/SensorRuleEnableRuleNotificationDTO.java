package com.iotassistant.controllers.dtos.sensorrules;

import com.iotassistant.models.notifications.SensorRuleEnableRuleNotification;

public class SensorRuleEnableRuleNotificationDTO extends SensorRuleNotificationDTO{
	
	private EnableRuleSensorRuleDTO enableRuleSensorRule;

	
	private boolean newSensorRuleState;

	public SensorRuleEnableRuleNotificationDTO(SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification) {
		super(sensorRuleEnableRuleNotification);
		this.enableRuleSensorRule = new EnableRuleSensorRuleDTO(sensorRuleEnableRuleNotification.getEnableSensorRule());
		this.newSensorRuleState = sensorRuleEnableRuleNotification.isSensorRuleNewState();
	}

	

	public EnableRuleSensorRuleDTO getEnableRuleSensorRule() {
		return enableRuleSensorRule;
	}



	public boolean isNewSensorRuleState() {
		return newSensorRuleState;
	}


	
}
