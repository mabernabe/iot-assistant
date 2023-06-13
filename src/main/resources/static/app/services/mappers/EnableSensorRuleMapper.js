class EnableSensorRuleMapper {
	
	constructor() {
		  this.sensorRuleMapper = new SensorRuleMapper();
	}
	
	buildEnableSensorRuleFromServiceObject(enableSensorRuleServiceObject) {
		var measureThresholdSettingsServiceObject = enableSensorRuleServiceObject.sensorMeasureThresholdSettings;
		var measureThresholdSettings = this.sensorRuleMapper.getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsServiceObject);
		var type = this.sensorRuleMapper.getSensorRuleTypeFromServiceObject(enableSensorRuleServiceObject);
		var id = this.sensorRuleMapper.getSensorRuleIdFromServiceObject(enableSensorRuleServiceObject);
		var enabled = this.sensorRuleMapper.getSensorRuleEnabledFromServiceObject(enableSensorRuleServiceObject);
		var timeBetweenTriggers = this.sensorRuleMapper.getSensorRuleTimeBetweenTriggersFromServiceObject(enableSensorRuleServiceObject);
		var notificationType = this.sensorRuleMapper.getSensorRuleNotificationTypeFromServiceObject(enableSensorRuleServiceObject);
		var sensorRuleId = enableSensorRuleServiceObject.sensorRuleId;
		var enableAction = enableSensorRuleServiceObject.enableAction;
		var enableSensorRule = new EnableRuleSensorRule(measureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, sensorRuleId, enableAction);
		return enableSensorRule;
	}
	
	buildEnableSensorRuleServiceObject(enableSensorRule) {
		var enableSensorRuleServiceObject =  this.sensorRuleMapper.buildSensorRuleServiceObject(enableSensorRule);
		enableSensorRuleServiceObject.sensorRuleId = enableSensorRule.getSensorRuleId();
		enableSensorRuleServiceObject.enableAction = enableSensorRule.isEnableAction();
		return enableSensorRuleServiceObject;
	}
	
}