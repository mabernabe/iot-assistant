package com.iotassistant.controllers.dtos.sensorrules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.sensorrules.EnableRuleSensorRule;
import com.iotassistant.models.sensorrules.SensorMeasureThresholdSettings;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;

public class EnableRuleSensorRuleDTO extends SensorRuleDTO {
	
	private int sensorRuleId;
	
	private boolean enableAction;

	public EnableRuleSensorRuleDTO() {
		super();
	}

	public EnableRuleSensorRuleDTO(EnableRuleSensorRule enableRuleSensorRule) {
		super(enableRuleSensorRule);
		this.sensorRuleId = enableRuleSensorRule.getSensorRuleId();
		this.enableAction = enableRuleSensorRule.isEnableAction();
	}

	@JsonIgnore
	public EnableRuleSensorRule getSensorRule() {
		SensorRuleTriggerIntervalEnum timeBetweenTriggers = SensorRuleTriggerIntervalEnum.getInstance(this.getTimeBetweenTriggers());
		SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
		return new EnableRuleSensorRule(sensorMeasureThresholdSettings, enabled, sensorRuleId, timeBetweenTriggers, NotificationTypeEnum.TELEGRAM, enableAction);

	}

	public int getSensorRuleId() {
		return sensorRuleId;
	}

	public void setSensorRuleId(int sensorRuleId) {
		this.sensorRuleId = sensorRuleId;
	}

	public boolean isEnableAction() {
		return enableAction;
	}

	public void setEnableAction(boolean enableAction) {
		this.enableAction = enableAction;
	}


}
