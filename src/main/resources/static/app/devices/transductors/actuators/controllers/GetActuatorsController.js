actuatorsModule.controller ("GetActuatorsController",function($scope, ActuatorAPIService, SweetAlertService, $interval){

	const FETCH_ACTUATORS_REFRESH_TIME_MS = 2000 ;
	
	var self = this;

	self.actuators = [];

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
		let fetchActuatorsInterval = $interval(fetchActuators, FETCH_ACTUATORS_REFRESH_TIME_MS);
		$scope.$on('$destroy',function(){
			if(fetchActuatorsInterval) {
				$interval.cancel(fetchActuatorsInterval);
			}
		})
	}
	
	initializeController();  

	self.setActuatorDigitalValue = function(actuator, value){
		$interval.cancel(intervalIstance);
		var newValue = ActuatorValue.getDigitalStringValue(!value.isHigh());
		value.setValue(newValue);
		ActuatorAPIService.setActuatorValue(actuator, value)
		.finally(function() { 
			intervalIstance = $interval(fetchActuators, FETCH_ACTUATORS_REFRESH_TIME_MS);
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
			intervalIstance = $interval(fetchActuators, FETCH_ACTUATORS_REFRESH_TIME_MS);
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
