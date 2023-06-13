var installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallAlarmSensorRuleController", function(SensorRuleAPIService){
	
	var self = this;

	
	self.allRequired = function(sensorRuleSettings) {
		var alarmSensorRule = buildAlarmSensorRule(sensorRuleSettings);
		return alarmSensorRule.isValid();
	}
	
	var buildAlarmSensorRule = function(sensorRuleSettings) {
		var sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		var sensorRuleType = sensorRuleSettings.sensorRuleType;
		var timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		var notificationType = sensorRuleSettings.notificationType;
		return new AlarmSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, true, timeBetweenTriggers, notificationType);
	}
	

	self.install = function(sensorRuleSettings) {
		var alarmSensorRule = buildAlarmSensorRule(sensorRuleSettings);
		var promise = SensorRuleAPIService.installAlarmSensorRule(alarmSensorRule);	
		return promise;
	}

});