var installActuatorController= angular.module('installActuatorController', ['installTransductorController', 'actuatorAPIService']);

installActuatorController.controller ("InstallActuatorController",function($scope, $controller, ActuatorAPIService){


	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Actuator";

	self.getTransductorSupportedProperties = function(devicesCapabilities) {
		return devicesCapabilities.getActuatorSupportedProperties();
	}

	self.getTransductorSupportedInterfaces = function(devicesCapabilities) {
		return devicesCapabilities.getActuatorSupportedInterfaces();
	}
	
	self.getTransductorSupportedWatchdogIntervals = function(devicesCapabilities) {
		return devicesCapabilities.getActuatorSupportedWatchdogIntervals();
	}
	
	
	self.installMQttInterfaceTransductor = function(actuator) {
		return ActuatorAPIService.installMQttInterfaceActuator(actuator);
	}

	self.getTransductorType= function() {
		return self.transductorType;
	}


});
