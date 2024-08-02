
actuatorsModule.controller ("InstallActuatorController",function($scope, $controller, ActuatorAPIService){


	let self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Actuator";
	
	let fetchActuatorCapabilities = function(){
		self.systemAPIService.getActuatorsCapabilities()
		.then(function(actuatorsCapabilities) { 
			self.supportedProperties = actuatorsCapabilities.getSupportedProperties();
			self.supportedWatchdogIntervals = actuatorsCapabilities.getSupportedWatchdogIntervals();
			self.supportedInterfaces = actuatorsCapabilities.getSupportedInterfaces();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchActuatorCapabilities();
	}
	
	initializeController();
	
	self.installMQttInterfaceTransductor = function(actuator) {
		return ActuatorAPIService.installMQttInterfaceActuator(actuator);
	}

});
