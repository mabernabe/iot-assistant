var installActuatorController= angular.module('installActuatorController', ['installTransductorController', 'actuatorAPIService']);

installActuatorController.controller ("InstallActuatorController",function($scope, $controller, ActuatorAPIService){


	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Actuator";
	
	var fetchActuatorCapabilities = function(){
		self.iotAssistantAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.supportedProperties = devicesCapabilities.getActuatorSupportedProperties();
			self.supportedWatchdogIntervals = devicesCapabilities.getActuatorSupportedWatchdogIntervals();
			self.supportedInterfaces = devicesCapabilities.getActuatorSupportedInterfaces();
		},function() {
		})
	}
	
	self.installMQttInterfaceTransductor = function(actuator) {
		return ActuatorAPIService.installMQttInterfaceActuator(actuator);
	}

	fetchActuatorCapabilities();

});
