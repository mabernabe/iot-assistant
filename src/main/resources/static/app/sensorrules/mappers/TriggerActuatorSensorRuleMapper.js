class TriggerActuatorSensorRuleMapper {
	
	constructor() {
		  this.sensorRuleMapper = new SensorRuleMapper();
	}
	
	buildTriggerActuatorSensorRuleFromServiceObject(triggerActuatorSensorRuleServiceObject) {
		let measureThresholdSettingsServiceObject = triggerActuatorSensorRuleServiceObject.sensorMeasureThresholdSettings;
		let measureThresholdSettings = this.sensorRuleMapper.getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsServiceObject);
		let type = this.sensorRuleMapper.getSensorRuleTypeFromServiceObject(triggerActuatorSensorRuleServiceObject);
		let id = this.sensorRuleMapper.getSensorRuleIdFromServiceObject(triggerActuatorSensorRuleServiceObject);
		let enabled = this.sensorRuleMapper.getSensorRuleEnabledFromServiceObject(triggerActuatorSensorRuleServiceObject);
		let timeBetweenTriggers = this.sensorRuleMapper.getSensorRuleTimeBetweenTriggersFromServiceObject(triggerActuatorSensorRuleServiceObject);
		let notificationType = this.sensorRuleMapper.getSensorRuleNotificationTypeFromServiceObject(triggerActuatorSensorRuleServiceObject);
		let actuatorSettings = this.#buildActuatorSetValue(triggerActuatorSensorRuleServiceObject);
		let triggerActuatorSensorRule = new TriggerActuatorSensorRule(measureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, actuatorSettings);
		return triggerActuatorSensorRule;
	}
	
	#buildActuatorSetValue(triggerActuatorSensorRuleServiceObject) {
		let actuatorSettings = new ActuatorSetValueSettings();
		actuatorSettings.setActuatorName(triggerActuatorSensorRuleServiceObject.actuatorName);
		let actuatorPropertyObject = triggerActuatorSensorRuleServiceObject.actuatorProperty;
		let actuatorProperty = new Property(actuatorPropertyObject.name, actuatorPropertyObject.nameWithUnit, actuatorPropertyObject.unit, actuatorPropertyObject.binary);
		actuatorSettings.setActuatorProperty(actuatorProperty);
		actuatorSettings.setActuatorSetValue(triggerActuatorSensorRuleServiceObject.actuatorSetValue);
		return actuatorSettings;

	}
	
	buildTriggerActuatorSensorRuleServiceObject(triggerActuatorSensorRule) {
		let triggerActuatorSensorRuleServiceObject =  this.sensorRuleMapper.buildSensorRuleServiceObject(triggerActuatorSensorRule);
		triggerActuatorSensorRuleServiceObject.actuatorName = triggerActuatorSensorRule.actuatorSettings.actuatorName;
		triggerActuatorSensorRuleServiceObject.actuatorProperty = triggerActuatorSensorRule.actuatorSettings.actuatorProperty;
		triggerActuatorSensorRuleServiceObject.actuatorSetValue = triggerActuatorSensorRule.actuatorSettings.actuatorSetValue;
		return triggerActuatorSensorRuleServiceObject;
	}
	

	
}