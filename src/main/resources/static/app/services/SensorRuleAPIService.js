var sensorRuleAPIService = angular.module('sensorRuleAPIService', ['restAPIService']);

sensorRuleAPIService.service("SensorRuleAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var ruleBaseUri = "rules/";
	
	var sensorRulesBaseUri = ruleBaseUri + 'sensorRules/';
	
	var sensorRulesMapper = new SensorRulesMapper();
	
	
	self.getSensorRules = function () {
		var deferred = $q.defer();
		RestAPIService.get(sensorRulesBaseUri).then(function(sensorRulesServiceObject) {
			deferred.resolve(sensorRulesMapper.buildSensorRulesFromServiceObject(sensorRulesServiceObject));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	
	self.installTriggerActuatorSensorRule = function (triggerActuatorSensorRule) {
		var deferred = $q.defer();
		var triggerActuatorSensorRuleServiceObject = sensorRulesMapper.buildTriggerActuatorSensorRuleServiceObject(triggerActuatorSensorRule);
		RestAPIService.post(ruleBaseUri + 'triggerActuatorSensorRules/', triggerActuatorSensorRuleServiceObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.installAlarmSensorRule = function (alarmSensorRule) {
		var deferred = $q.defer();
		var alarmSensorRuleServiceObject = sensorRulesMapper.buildAlarmSensorRuleServiceObject(alarmSensorRule);
		RestAPIService.post(ruleBaseUri + 'alarmSensorRules/', alarmSensorRuleServiceObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

	
	self.installEnableSensorRule = function (enableSensorRule) {
		var deferred = $q.defer();
		var enableSensorRuleServiceObject = sensorRulesMapper.buildEnableSensorRuleServiceObject(enableSensorRule);
		RestAPIService.post(ruleBaseUri + 'enableSensorRules/', enableSensorRuleServiceObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.installCameraSensorRule = function (cameraSensorRule) {
		var deferred = $q.defer();
		var cameraSensorRuleServiceObject = sensorRulesMapper.buildCameraSensorRuleServiceObject(cameraSensorRule);
		RestAPIService.post(ruleBaseUri + 'cameraSensorRules/', cameraSensorRuleServiceObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.deleteSensorRule = function (ruleId) {
		var deferred = $q.defer();
		RestAPIService.delete(sensorRulesBaseUri.concat(ruleId)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableSensorRule = function (enable, ruleId) {
		var deferred = $q.defer();
		var enableServiceObject = {};
		enableServiceObject.enable = enable;
		RestAPIService.patch(sensorRulesBaseUri.concat(ruleId), enableServiceObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

});

