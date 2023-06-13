var installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallEnableRuleSensorRuleController", function(SensorRuleAPIService){
	
	var self = this;

	self.sensorRuleId;
	
	self.enableAction;
	
	self.sensorRules = [];
	
	var initializeController = function() {
		getSensorRules();
	}
	
	var getSensorRules = function(){
		SensorRuleAPIService.getSensorRules()
		.then(function(sensorRules) { 
			self.sensorRules = sensorRules.getAllSensorRules();
		},function() {
			self.sensorRules = [];
		})
	}
	
	self.allRequired = function(sensorRuleSettings) {
		var enableRuleSensorRule = buildEnableRuleSensorRule(sensorRuleSettings);
		return enableRuleSensorRule.isValid();
	}
	
	var buildEnableRuleSensorRule = function(sensorRuleSettings) {
		var sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		var sensorRuleType = sensorRuleSettings.sensorRuleType;
		var timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		var notificationType = sensorRuleSettings.notificationType;
		var enabled = true;
		return new EnableRuleSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, enabled, timeBetweenTriggers, notificationType, self.sensorRuleId, self.enableAction);
	}
	

	self.install  = function(sensorRuleSettings) {
		var enableSensorRule =  buildEnableRuleSensorRule(sensorRuleSettings);
		var promise = SensorRuleAPIService.installEnableSensorRule(enableSensorRule);	
		return promise;
	}
	
	initializeController();

});