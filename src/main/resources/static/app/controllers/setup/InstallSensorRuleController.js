
sensorRulesModule.controller ("InstallSensorRuleController", function($scope, $controller, IotAssistantAPIService, SensorAPIService, SweetAlertService, $route){

	let self = this;
	
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
	
	let fetchSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;
		},function() {
			self.sensors = [];
		})
	}
	
	let fetchSensorRulesCapabilities = function(){
		IotAssistantAPIService.getRulesCapabilities()
		.then(function(rulesCapabilities) { 
			self.sensorRulesCapabilities = rulesCapabilities.getSensorRulesCapabilities();
			self.supportedNotificationsTypes = rulesCapabilities.SupportedNotificationsTypes();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchSensors();
		fetchSensorRulesCapabilities();
	}
	
	initializeController();
	
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
		let sensorProperty = self.sensorRuleSettings.sensorMeasureThresholdSettings.sensorProperty;
		return sensorProperty != null && 'name' in sensorProperty;
	}
	
	self.allRequired = function() {
		sensorRuleType = self.sensorRuleSettings.sensorRuleType;
		let isSensorRuleTypeSelected = sensorRuleType != null;
		let allRequired = false;
		if (isSensorRuleTypeSelected) {
			let sensorRuleController = getSensorRuleController();			
			allRequired = sensorRuleController.allRequired(self.sensorRuleSettings);
		}	
		return allRequired;
	}
	
	
	let getSensorRuleController = function() {
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
		let sensorRuleController = getSensorRuleController(sensorRuleType);	
		let promise = sensorRuleController.install(self.sensorRuleSettings);
		promise.then(function() {
			let redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Sensor rule installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Sensor rule installation failed' + ' \n Error: ' + error.data.message);
		})		
	}

});
