var getTransductorsController= angular.module('getTransductorsControllers', ['sensorAPIService', 'actuatorAPIService', 'sweetAlertService']);

getTransductorsController.controller ("GetSensorsController",function($scope, SensorAPIService, $interval, SweetAlertService){

	const SENSORS_REFRESH_TIME_MS = 2000 ;

	var self = this;

	self.sensors = [];

	self.getSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;	
		},function() {
			self.sensors = [];
		})
	}
	
	self.deleteSensor = function(sensorName){
		function deleteSensor() {
			SensorAPIService.deleteSensor(sensorName)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Sensor deleted');
			},function() {
				SweetAlertService.showErrorAlert('Sensor deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete sensor ' + sensorName + '?', deleteSensor);
	}
	
	self.enableWatchdog = function(enable, sensorName){
		enableString = (enable ? 'enabled' : 'disabled') ;
		SensorAPIService.enableWatchdog(enable, sensorName)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Watchdog ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Watchdog could not be ' + enableString);
		})
	}
	
	self.getSensors();   
	
	var intervalIstance = $interval(self.getSensors, SENSORS_REFRESH_TIME_MS);

	$scope.$on('$destroy',function(){
		if(intervalIstance) {
			$interval.cancel(intervalIstance);
		}
	})

});



getTransductorsController.controller ("GetActuatorsController",function($scope, ActuatorAPIService, SweetAlertService, $interval){

	const ACTUATORS_REFRESH_TIME_MS = 2000 ;
	
	var self = this;

	self.actuators = [];

	self.getActuators = function(){
		ActuatorAPIService.getActuators()
		.then(function(actuators) { 
			self.actuators = actuators;	
		},function() {
			self.actuators = [];
		})
	}
	self.getActuators();   
	var intervalIstance = $interval(self.getActuators, ACTUATORS_REFRESH_TIME_MS);

	$scope.$on('$destroy',function(){
		if(intervalIstance) {
			$interval.cancel(intervalIstance);
		}
	})

	self.setActuatorDigitalValue = function(actuator, value){
		$interval.cancel(intervalIstance);
		var newValue = ActuatorValue.getDigitalStringValue(!value.isHigh());
		value.setValue(newValue);
		ActuatorAPIService.setActuatorValue(actuator, value)
		.finally(function() { 
			intervalIstance = $interval(self.getActuators, ACTUATORS_REFRESH_TIME_MS);
		})
	}
	
	self.setActuatorAnalogValue = function(actuator, propertyActuated, value){
		let newValue = document.getElementById(`newValue-${actuator.getName()}-${propertyActuated.getShortName()}`).value;
		if (isNaN(newValue) ||  newValue === "") {
			return;
		}
		$interval.cancel(intervalIstance);
		value.setValue(newValue.toString());
		ActuatorAPIService.setActuatorValue(actuator, value)
		.finally(function() { 
			intervalIstance = $interval(self.getActuators, ACTUATORS_REFRESH_TIME_MS);
		})
	}
	
	self.deleteActuator = function(actuatorName){
		function deleteActuator() {
			ActuatorAPIService.deleteActuator(actuatorName)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Actuator deleted');
			},function() {
				SweetAlertService.showErrorAlert('Actuator deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete actuator ' + actuatorName + '?', deleteActuator);
	}
	
	self.enableWatchdog = function(enable, actuatorName){
		enableString = (enable ? 'enabled' : 'disabled') ;
		ActuatorAPIService.enableWatchdog(enable, actuatorName)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Watchdog ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Watchdog could not be ' + enableString);
		})
	}



});
