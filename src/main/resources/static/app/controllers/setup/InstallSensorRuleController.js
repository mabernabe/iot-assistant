var installSensorRuleController= angular.module('installSensorRuleController', ['iotAssistantAPIService', 'sweetAlertService', 'actuatorAPIService', 'sensorAPIService', 'sensorRuleAPIService']);


installSensorRuleController.controller ("InstallSensorRuleController", function($scope, $controller, IotAssistantAPIService, SensorAPIService, SweetAlertService, $route){

	var self = this;
	
	self.sensors = [];
	
	self.sensorRuleSettings = {sensorMeasureThresholdSettings: new SensorMeasureThresholdSettings(), sensorRuleType : null, 
							   timeBetweenTriggers: null, notificationType: null};

	
	self.sensorPropertiesOptions =  [];
	
	self.installAlarmSensorRuleController = $controller('InstallAlarmSensorRuleController', {$scope: $scope.$new()});

	self.installEnableRuleSensorRuleController = $controller('InstallEnableRuleSensorRuleController', {$scope: $scope.$new()});
	
	self.installTriggerActuatorSensorRuleController = $controller('InstallTriggerActuatorSensorRuleController', {$scope: $scope.$new()});
	
	self.installCameraSensorRuleController = $controller('InstallCameraSensorRuleController', {$scope: $scope.$new()});
	
	self.sensorRulesCapabilities;
	
	self.supportedNotificationsTypes;
	
	var initializeController = function() {
		getSensors();
		getSensorRulesCapabilities();
	}
	

	var getSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;
		},function() {
			self.sensors = [];
		})
	}
	
	var getSensorRulesCapabilities = function(){
		IotAssistantAPIService.getRulesCapabilities()
		.then(function(rulesCapabilities) { 
			self.sensorRulesCapabilities = rulesCapabilities.getSensorRulesCapabilities();
			self.supportedNotificationsTypes = rulesCapabilities.SupportedNotificationsTypes();
		},function() {
		})
	}
	
	self.updateSensorPropertiesOptions = function(){		
		self.sensors.forEach(sensor  => {
			if (sensor.getName() == self.sensorRuleSettings.sensorMeasureThresholdSettings.getSensorName()) {
				self.sensorPropertiesOptions = sensor.getProperties();
			}
		})
	}
	
	
	self.setSensorRuleType = function(sensorRuleType) {
		self.sensorRuleSettings.sensorRuleType = sensorRuleType;
	}
	
	
	self.isSensorPropertySelected = function() {
		var sensorProperty = self.sensorRuleSettings.sensorMeasureThresholdSettings.sensorProperty;
		return sensorProperty != null && 'name' in sensorProperty;
	}
	
	self.allRequired = function() {
		sensorRuleType = self.sensorRuleSettings.sensorRuleType;
		var isSensorRuleTypeSelected = sensorRuleType != null;
		var allRequired = false;
		if (isSensorRuleTypeSelected) {
			var sensorRuleController = getSensorRuleController();			
			allRequired = sensorRuleController.allRequired(self.sensorRuleSettings);
		}	
		return allRequired;
	}
	
	
	var getSensorRuleController = function() {
		let sensorRuleTypeSelected = self.sensorRuleSettings.sensorRuleType
		if (sensorRuleTypeSelected == AlarmSensorRule.alarmSensorRuleType) {
				return self.installAlarmSensorRuleController;
		}
		if (self.isEnableSensorRuleTypeSelected()) {
				return self.installEnableRuleSensorRuleController;
		}
		if (self.isTriggerActuatorSensorRuleTypeSelected()) {
				return self.installTriggerActuatorSensorRuleController;
		}
		if (self.isCameraSensorRuleTypeSelected()) {
				return self.installCameraSensorRuleController;
		}
	}
	
	
	self.isEnableSensorRuleTypeSelected = function() {
		return self.sensorRuleSettings.sensorRuleType == EnableRuleSensorRule.enableRuleSensorRuleType;
	}
	
	self.isTriggerActuatorSensorRuleTypeSelected = function() {
		return self.sensorRuleSettings.sensorRuleType == TriggerActuatorSensorRule.triggerActuatorSensorRuleType;
	}
	
	self.isCameraSensorRuleTypeSelected = function() {
		return self.sensorRuleSettings.sensorRuleType == CameraSensorRule.cameraSensorRuleType;
	}

	self.installAndRedirect = function() {
		sensorRuleType = self.sensorRuleSettings.sensorRuleType;
		var sensorRuleController = getSensorRuleController(sensorRuleType);	
		var promise = sensorRuleController.install(self.sensorRuleSettings);
		promise.then(function() {
			var redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Sensor rule installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Sensor rule installation failed' + ' \n Error: ' + error.data.message);
		})		
	}
	
	initializeController();
	
	

});
