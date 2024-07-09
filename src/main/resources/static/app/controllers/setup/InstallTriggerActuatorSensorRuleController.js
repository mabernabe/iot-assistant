

sensorRulesModule.controller ("InstallTriggerActuatorSensorRuleController", function(SensorRuleAPIService, ActuatorAPIService){
	
	let self = this;
	
	self.actuators = [];

	self.actuatorSettings = new ActuatorSetValueSettings();
	
	self.actuatorPropertiesOptions =  [];
	
	let fetchActuators = function(){
		ActuatorAPIService.getActuators()
		.then(function(actuators) { 
			self.actuators = actuators;	
		},function() {
			self.actuators = [];
		})
	}
	
	let initializeController = function() {
		fetchActuators();
	}
	
	initializeController();
	
	self.isActuatorPropertySelected = function() {
		let actuatorProperty = self.actuatorSettings.actuatorProperty;
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
		let triggerActuatorSensorRule = buildTriggerActuatorSensorRule(sensorRuleSettings);
		return triggerActuatorSensorRule.isValid();
	}
	
	let buildTriggerActuatorSensorRule = function(sensorRuleSettings) {
		let sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		let sensorRuleType = sensorRuleSettings.sensorRuleType;
		let timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		let notificationType = sensorRuleSettings.notificationType;
		let enabled = true;
		return new TriggerActuatorSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, enabled, timeBetweenTriggers, notificationType, self.actuatorSettings);
	}
	

	self.install = function(sensorRuleSettings) {
		let triggerActuatorSensorRule =  buildTriggerActuatorSensorRule(sensorRuleSettings);
		let promise = SensorRuleAPIService.installTriggerActuatorSensorRule(triggerActuatorSensorRule);	
		return promise;
	}

});