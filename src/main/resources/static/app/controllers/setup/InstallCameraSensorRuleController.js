var installSensorRulesModule= angular.module('installSensorRuleController');

installSensorRulesModule.controller ("InstallCameraSensorRuleController", function(SensorRuleAPIService, CameraAPIService){
	
	var self = this;
	
	self.cameras = [];

	self.cameraName;			
	
	var initializeController = function() {
		getCameras();
	}
	
	var getCameras = function(){
		CameraAPIService.getCameras()
		.then(function(cameras) { 
			self.cameras = cameras;	
		},function() {
			self.cameras = [];
		})
	}
	
	
	self.allRequired = function(sensorRuleSettings) {
		var cameraSensorRule = buildCameraSensorRule(sensorRuleSettings, self.cameraName);
		return cameraSensorRule.isValid();
	}
	
	var buildCameraSensorRule = function(sensorRuleSettings, cameraName) {
		var sensorMeasureThresholdSettings = sensorRuleSettings.sensorMeasureThresholdSettings;
		var sensorRuleType = sensorRuleSettings.sensorRuleType;
		var timeBetweenTriggers = sensorRuleSettings.timeBetweenTriggers;
		var notificationType = sensorRuleSettings.notificationType;
		var enabled = true;
		return new CameraSensorRule(sensorMeasureThresholdSettings, sensorRuleType, null, enabled, timeBetweenTriggers, notificationType, cameraName);
	}
	

	self.install = function(sensorRuleSettings) {
		var cameraSensorRule =  buildCameraSensorRule(sensorRuleSettings,  self.cameraName);
		var promise = SensorRuleAPIService.installCameraSensorRule(cameraSensorRule);	
		return promise;
	}
	
	initializeController();

});