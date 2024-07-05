var installSensorController= angular.module('installSensorController', ['installTransductorController', 'sensorAPIService']);


installSensorController.controller ("InstallSensorController",function($scope, $controller, SensorAPIService){

	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Sensor";

	self.getTransductorSupportedProperties = function(devicesCapabilities) {
		return devicesCapabilities.getSensorSupportedProperties();
	}

	self.getTransductorSupportedInterfaces = function(devicesCapabilities) {
		return devicesCapabilities.getSensorSupportedInterfaces();
	}
	
	self.getTransductorSupportedWatchdogIntervals = function(devicesCapabilities) {
		return devicesCapabilities.getSensorSupportedWatchdogIntervals();
	}
	
	self.installMQttInterfaceTransductor = function(transductor) {
		return SensorAPIService.installMQttInterfaceSensor(transductor);
	}

	self.getTransductorType= function() {
		return self.transductorType;
	}


});
