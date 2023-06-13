var installSensorController= angular.module('installSensorController', ['installTransductorController', 'sensorAPIService']);


installSensorController.controller ("InstallSensorController",function($scope, $controller, SensorAPIService){

	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Sensor";

	self.getTransductorSupportedProperties = function(stationCapabilities) {
		return stationCapabilities.getSensorSupportedProperties();
	}

	self.getTransductorSupportedInterfaces = function(stationCapabilities) {
		return stationCapabilities.getSensorSupportedInterfaces();
	}
	
	self.getTransductorSupportedWatchdogIntervals = function(stationCapabilities) {
		return stationCapabilities.getSensorSupportedWatchdogIntervals();
	}

	self.isTransductorPinAvailable = function(stationCapabilities, propertyMeasuredName) {
		return stationCapabilities.isSensorPinAvailable(propertyMeasuredName);
	}

	self.getTransductorAvailablePinIds = function(stationCapabilities, propertyMeasuredName) {
		return stationCapabilities.getSensorAvailablePinIds(propertyMeasuredName);
	}

	self.installPinInterfaceTransductor = function(sensor, sensorPinInterface) {
		return SensorAPIService.installPinInterfaceSensor( sensor, sensorPinInterface);
	}
	
	
	self.installMQttInterfaceTransductor = function(transductor) {
		return SensorAPIService.installMQttInterfaceSensor(transductor);
	}

	self.getTransductorType= function() {
		return self.transductorType;
	}


});
