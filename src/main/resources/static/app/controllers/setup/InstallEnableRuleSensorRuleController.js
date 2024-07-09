let installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallEnableRuleSensorRuleController", function(SensorRuleAPIService){
	
	let self = this;

	self.sensorRuleId;
	
	self.enableAction;
	
	self.sensorRules = [];
	
	let fetchSensorRules = function(){
		SensorRuleAPIService.getSensorRules()
		.then(function(sensorRules) { 
			self.sensorRules = sensorRules.getAllSensorRules();
		},function() {
			self.sensorRules = [];
		})
	}
	
	let initializeController = function() {
		fetchSensorRules();
	}
	
	initializeController();
	
	self.allRequired = function(sensorRuleSettings) {
		let enableRuleSensorRule = buildEnableRuleSensorRule(sensorRuleSettings);
		return enableRuleSensorRule.isValid();
	}
	
	let buildEnableRuleSensorRule = function(sensorRuleSettings) {
		let sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		let sensorRuleType = sensorRuleSettings.sensorRuleType;
		let timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		let notificationType = sensorRuleSettings.notificationType;
		let enabled = true;
		return new EnableRuleSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, enabled, timeBetweenTriggers, notificationType, self.sensorRuleId, self.enableAction);
	}
	

	self.install  = function(sensorRuleSettings) {
		let enableSensorRule =  buildEnableRuleSensorRule(sensorRuleSettings);
		let promise = SensorRuleAPIService.installEnableSensorRule(enableSensorRule);	
		return promise;
	}

});