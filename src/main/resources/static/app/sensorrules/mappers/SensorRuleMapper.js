class SensorRuleMapper {
	
	constructor() {
	}
	
	
	getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsObject) {
	 	var sensorMeasureThresholdSettings = new SensorMeasureThresholdSettings();
		sensorMeasureThresholdSettings.setSensorName(measureThresholdSettingsObject.sensorName);
		var sensorProperty = new Property(measureThresholdSettingsObject.sensorProperty.name, measureThresholdSettingsObject.sensorProperty.digital);
		sensorMeasureThresholdSettings.setSensorProperty(sensorProperty);
		sensorMeasureThresholdSettings.setSensorValueThreshold(measureThresholdSettingsObject.sensorValueThreshold);
		sensorMeasureThresholdSettings.setSensorAnalogThresholdOperator(measureThresholdSettingsObject.sensorAnalogThresholdOperator);
		return sensorMeasureThresholdSettings;
	}
	
	getSensorRuleTypeFromServiceObject(sensorRuleServiceObject) {
		return sensorRuleServiceObject.type;
	}
	
	getSensorRuleIdFromServiceObject(sensorRuleServiceObject) {
		return sensorRuleServiceObject.id;
	}
	
	getSensorRuleEnabledFromServiceObject(sensorRuleServiceObject) {
		return sensorRuleServiceObject.enabled;
	}
	
	getSensorRuleTimeBetweenTriggersFromServiceObject(sensorRuleServiceObject) {
		return sensorRuleServiceObject.timeBetweenTriggers;
	}
	
	getSensorRuleNotificationTypeFromServiceObject(sensorRuleServiceObject) {
		return sensorRuleServiceObject.notificationType;
	}
	
	buildSensorRuleServiceObject(sensorRule) {
		var sensorRuleServiceObject = {};
		sensorRuleServiceObject.timeBetweenTriggers = sensorRule.getTimeBetweenTriggers();
		sensorRuleServiceObject.notificationType = sensorRule.getNotificationType();
		sensorRuleServiceObject.enabled = sensorRule.isEnabled();
		sensorRuleServiceObject.sensorMeasureThresholdSettings = this.#buildSensorMeasureThresholdSettingsServiceObject(sensorRule);
		sensorRuleServiceObject.type = sensorRule.getType();		
		return sensorRuleServiceObject;
	}
	
	#buildSensorMeasureThresholdSettingsServiceObject(sensorRule) {
		var sensorMeasureThresholdSettingsObject = sensorRule.sensorMeasureThresholdSettings;
		sensorMeasureThresholdSettingsObject.sensorName = sensorRule.getSensorName();
		sensorMeasureThresholdSettingsObject.sensorProperty = sensorRule.getSensorProperty();
		sensorMeasureThresholdSettingsObject.sensorValueThreshold = sensorRule.getSensorValueThreshold();
		sensorMeasureThresholdSettingsObject.sensorAnalogThresholdOperator = sensorRule.getSensorAnalogThresholdOperator();
		return sensorMeasureThresholdSettingsObject;
	}
	
}