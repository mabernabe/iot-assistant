class SensorRuleMapper {
	
	constructor() {
	}
	
	
	getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsObject) {
	 	let sensorMeasureThresholdSettings = new SensorMeasureThresholdSettings();
		sensorMeasureThresholdSettings.setSensorName(measureThresholdSettingsObject.sensorName);
		let sensorPropertyObject = measureThresholdSettingsObject.sensorProperty;
		let sensorProperty = new Property(sensorPropertyObject.name, sensorPropertyObject.nameWithUnit, sensorPropertyObject.unit, sensorPropertyObject.binary);
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
		let sensorRuleServiceObject = {};
		sensorRuleServiceObject.timeBetweenTriggers = sensorRule.getTimeBetweenTriggers();
		sensorRuleServiceObject.notificationType = sensorRule.getNotificationType();
		sensorRuleServiceObject.enabled = sensorRule.isEnabled();
		sensorRuleServiceObject.sensorMeasureThresholdSettings = this.#buildSensorMeasureThresholdSettingsServiceObject(sensorRule);
		sensorRuleServiceObject.type = sensorRule.getType();		
		return sensorRuleServiceObject;
	}
	
	#buildSensorMeasureThresholdSettingsServiceObject(sensorRule) {
		let sensorMeasureThresholdSettingsObject = sensorRule.sensorMeasureThresholdSettings;
		sensorMeasureThresholdSettingsObject.sensorName = sensorRule.getSensorName();
		sensorMeasureThresholdSettingsObject.sensorProperty = sensorRule.getSensorProperty();
		sensorMeasureThresholdSettingsObject.sensorValueThreshold = sensorRule.getSensorValueThreshold();
		sensorMeasureThresholdSettingsObject.sensorAnalogThresholdOperator = sensorRule.getSensorAnalogThresholdOperator();
		return sensorMeasureThresholdSettingsObject;
	}
	
}