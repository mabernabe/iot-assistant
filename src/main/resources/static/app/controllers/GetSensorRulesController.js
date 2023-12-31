var getSensorRulesController= angular.module('getSensorRulesController', ['sensorRuleAPIService', 'sweetAlertService']);


getSensorRulesController.controller("GetSensorRulesController",function($scope, SensorRuleAPIService, SweetAlertService, $interval){

	const RULES_REFRESH_TIME_MS = 5000 ;

	var self = this;
	
	self.alarmSensorRules = [];
	
	self.cameraSensorRules = [];
	
	self.enableRuleSensorRules = [];
	
	self.triggerActuatorSensorRules = [];
	
	var initializeController = function() {
		getSensorRules();   
		var refreshRulesInterval = $interval(getSensorRules, RULES_REFRESH_TIME_MS);
		$scope.$on('$destroy',function(){
			if(refreshRulesInterval) {
				$interval.cancel(refreshRulesInterval);
			}
		})
	}

	getSensorRules = function(){		
		SensorRuleAPIService.getSensorRules()
		.then(function(sensorRules) { 
			self.alarmSensorRules = sensorRules.getAlarmSensorRules();
			self.enableRuleSensorRules = sensorRules.getEnableSensorRules();
			self.triggerActuatorSensorRules = sensorRules.getTriggerActuatorSensorRules();
			self.cameraSensorRules = sensorRules.getCameraSensorRules();
		},function() {
			self.alarmSensorRules = [];
			self.enableRuleSensorRules = [];	
			self.triggerActuatorSensorRules = [];
		})
	}

	
	self.enableSensorRule = function(enable, ruleId){
		enableString = (enable ? 'enabled' : 'disabled') ;
		SensorRuleAPIService.enableSensorRule(enable, ruleId)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Sensor rule ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Sensor rule could not be ' + enableString);
		})
	}
	
	
	self.deleteSensorRule = function(sensorRuleId){
		function deleteRule() {
			SensorRuleAPIService.deleteSensorRule(sensorRuleId)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Sensor rule deleted');
			},function() {
				SweetAlertService.showErrorAlert('Sensor rule deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete sensor rule ' + sensorRuleId + '?', deleteRule);
	}
	
	
	initializeController();

	
	

});
