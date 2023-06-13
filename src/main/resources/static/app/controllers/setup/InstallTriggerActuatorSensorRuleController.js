var installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallTriggerActuatorSensorRuleController", function(SensorRuleAPIService, ActuatorAPIService){
	
	var self = this;
	
	self.actuators = [];

	self.actuatorSettings = new ActuatorSetValueSettings();
	
	self.actuatorPropertiesOptions =  [];
				
	
	var initializeController = function() {
		getActuators();
	}
	
	var getActuators = function(){
		ActuatorAPIService.getActuators()
		.then(function(actuators) { 
			self.actuators = actuators;	
		},function() {
			self.actuators = [];
		})
	}
	
	self.isActuatorPropertySelected = function() {
		var actuatorProperty = self.actuatorSettings.actuatorProperty;
		return actuatorProperty != null && 'name' in actuatorProperty;
	}
	
	self.updateActuatorPropertiesOptions = function(){	
		self.actuators.forEach(actuator  => {
			if (actuator.getName() == self.actuatorSettings.getActuatorName()) {
				self.actuatorPropertiesOptions = actuator.getProperties();
			}
		})
	}
	
	self.allRequired = function(sensorRuleSettings) {
		var triggerActuatorSensorRule = buildTriggerActuatorSensorRule(sensorRuleSettings);
		return triggerActuatorSensorRule.isValid();
	}
	
	var buildTriggerActuatorSensorRule = function(sensorRuleSettings) {
		var sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		var sensorRuleType = sensorRuleSettings.sensorRuleType;
		var timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		var notificationType = sensorRuleSettings.notificationType;
		var enabled = true;
		return new TriggerActuatorSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, enabled, timeBetweenTriggers, notificationType, self.actuatorSettings);
	}
	

	self.install = function(sensorRuleSettings) {
		var triggerActuatorSensorRule =  buildTriggerActuatorSensorRule(sensorRuleSettings);
		var promise = SensorRuleAPIService.installTriggerActuatorSensorRule(triggerActuatorSensorRule);	
		return promise;
	}
	
	initializeController();

});