class TriggerActuatorSensorRuleMapper {
	
	constructor() {
		  this.sensorRuleMapper = new SensorRuleMapper();
	}
	
	buildTriggerActuatorSensorRuleFromServiceObject(triggerActuatorSensorRuleServiceObject) {
		var measureThresholdSettingsServiceObject = triggerActuatorSensorRuleServiceObject.sensorMeasureThresholdSettings;
		var measureThresholdSettings = this.sensorRuleMapper.getMeasureThresholdSettingsFromServiceObject(measureThresholdSettingsServiceObject);
		var type = this.sensorRuleMapper.getSensorRuleTypeFromServiceObject(triggerActuatorSensorRuleServiceObject);
		var id = this.sensorRuleMapper.getSensorRuleIdFromServiceObject(triggerActuatorSensorRuleServiceObject);
		var enabled = this.sensorRuleMapper.getSensorRuleEnabledFromServiceObject(triggerActuatorSensorRuleServiceObject);
		var timeBetweenTriggers = this.sensorRuleMapper.getSensorRuleTimeBetweenTriggersFromServiceObject(triggerActuatorSensorRuleServiceObject);
		var notificationType = this.sensorRuleMapper.getSensorRuleNotificationTypeFromServiceObject(triggerActuatorSensorRuleServiceObject);
		var actuatorSettings = this.#buildActuatorSetValue(triggerActuatorSensorRuleServiceObject);
		var triggerActuatorSensorRule = new TriggerActuatorSensorRule(measureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, actuatorSettings);
		return triggerActuatorSensorRule;
	}
	
	#buildActuatorSetValue(triggerActuatorSensorRuleServiceObject) {
		var actuatorSettings = new ActuatorSetValueSettings();
		actuatorSettings.setActuatorName(triggerActuatorSensorRuleServiceObject.actuatorName);
		var actuatorProperty = new Property(triggerActuatorSensorRuleServiceObject.actuatorProperty.name, triggerActuatorSensorRuleServiceObject.actuatorProperty.isDigital);
		actuatorSettings.setActuatorProperty(actuatorProperty);
		actuatorSettings.setActuatorSetValue(triggerActuatorSensorRuleServiceObject.actuatorSetValue);
		return actuatorSettings;

	}
	
	buildTriggerActuatorSensorRuleServiceObject(triggerActuatorSensorRule) {
		var triggerActuatorSensorRuleServiceObject =  this.sensorRuleMapper.buildSensorRuleServiceObject(triggerActuatorSensorRule);
		triggerActuatorSensorRuleServiceObject.actuatorName = triggerActuatorSensorRule.actuatorSettings.actuatorName;
		triggerActuatorSensorRuleServiceObject.actuatorProperty = triggerActuatorSensorRule.actuatorSettings.actuatorProperty;
		triggerActuatorSensorRuleServiceObject.actuatorSetValue = triggerActuatorSensorRule.actuatorSettings.actuatorSetValue;
		return triggerActuatorSensorRuleServiceObject;
	}
	

	
}