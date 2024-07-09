let installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallAlarmSensorRuleController", function(SensorRuleAPIService){
	
	let self = this;

	
	self.allRequired = function(sensorRuleSettings) {
		let alarmSensorRule = buildAlarmSensorRule(sensorRuleSettings);
		return alarmSensorRule.isValid();
	}
	
	let buildAlarmSensorRule = function(sensorRuleSettings) {
		let sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		let sensorRuleType = sensorRuleSettings.sensorRuleType;
		let timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		let notificationType = sensorRuleSettings.notificationType;
		return new AlarmSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, true, timeBetweenTriggers, notificationType);
	}
	

	self.install = function(sensorRuleSettings) {
		let alarmSensorRule = buildAlarmSensorRule(sensorRuleSettings);
		let promise = SensorRuleAPIService.installAlarmSensorRule(alarmSensorRule);	
		return promise;
	}

});