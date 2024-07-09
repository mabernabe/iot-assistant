let installActuatorController= angular.module('installActuatorController', ['installTransductorController', 'actuatorAPIService']);

installActuatorController.controller ("InstallActuatorController",function($scope, $controller, ActuatorAPIService){


	let self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Actuator";
	
	let fetchActuatorCapabilities = function(){
		self.iotAssistantAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.supportedProperties = devicesCapabilities.getActuatorSupportedProperties();
			self.supportedWatchdogIntervals = devicesCapabilities.getActuatorSupportedWatchdogIntervals();
			self.supportedInterfaces = devicesCapabilities.getActuatorSupportedInterfaces();
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
