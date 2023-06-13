class CameraSensorRuleMapper {
	
	constructor() {
		  this.sensorRuleMapper = new SensorRuleMapper();
	}
	
	buildCameraSensorRuleFromServiceObject(cameraSensorRuleServiceObject) {
		var measureThresholdSettingsServiceObject = cameraSensorRuleServiceObject.sensorMeasureThresholdSettings;
		var measureThresholdSettings = this.sensorRuleMapper.getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsServiceObject);
		var type = this.sensorRuleMapper.getSensorRuleTypeFromServiceObject(cameraSensorRuleServiceObject);
		var id = this.sensorRuleMapper.getSensorRuleIdFromServiceObject(cameraSensorRuleServiceObject);
		var enabled = this.sensorRuleMapper.getSensorRuleEnabledFromServiceObject(cameraSensorRuleServiceObject);
		var timeBetweenTriggers = this.sensorRuleMapper.getSensorRuleTimeBetweenTriggersFromServiceObject(cameraSensorRuleServiceObject);
		var notificationType = this.sensorRuleMapper.getSensorRuleNotificationTypeFromServiceObject(cameraSensorRuleServiceObject);
		var cameraName = cameraSensorRuleServiceObject.cameraName;
		var cameraSensorRule = new CameraSensorRule(measureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, cameraName);
		return cameraSensorRule;
	}
	
	
	buildCameraSensorRuleServiceObject(cameraSensorRule) {
		var cameraSensorRuleServiceObject =  this.sensorRuleMapper.buildSensorRuleServiceObject(cameraSensorRule);
		cameraSensorRuleServiceObject.cameraName = cameraSensorRule.getCameraName();
		return cameraSensorRuleServiceObject;
	}
	

	
}