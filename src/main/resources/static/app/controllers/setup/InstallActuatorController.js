var installActuatorController= angular.module('installActuatorController', ['installTransductorController', 'actuatorAPIService']);

installActuatorController.controller ("InstallActuatorController",function($scope, $controller, ActuatorAPIService){


	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Actuator";

	self.getTransductorSupportedProperties = function(stationCapabilities) {
		return stationCapabilities.getActuatorSupportedProperties();
	}

	self.getTransductorSupportedInterfaces = function(stationCapabilities) {
		return stationCapabilities.getActuatorSupportedInterfaces();
	}
	
	self.getTransductorSupportedWatchdogIntervals = function(stationCapabilities) {
		return stationCapabilities.getActuatorSupportedWatchdogIntervals();
	}

	self.isTransductorPinAvailable = function(stationCapabilities, propertyMeasuredName) {
		return stationCapabilities.isActuatorPinAvailable(propertyMeasuredName);
	}

	self.getTransductorAvailablePinIds = function(stationCapabilities, propertyMeasuredName) {
		return stationCapabilities.getActuatorAvailablePinIds(propertyMeasuredName);
	}

	self.installPinInterfaceTransductor = function(actuator, actuatorPinInterface) {
		return ActuatorAPIService.installPinInterfaceActuator(actuator, actuatorPinInterface);
	}
	
	
	self.installMQttInterfaceTransductor = function(actuator) {
		return ActuatorAPIService.installMQttInterfaceActuator(actuator);
	}

	self.getTransductorType= function() {
		return self.transductorType;
	}


});
