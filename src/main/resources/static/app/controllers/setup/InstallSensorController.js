var installSensorController= angular.module('installSensorController', ['installTransductorController', 'sensorAPIService']);


installSensorController.controller ("InstallSensorController",function($scope, $controller, SensorAPIService){

	var self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Sensor";
	
	var fetchSensorCapabilities = function(){
		self.iotAssistantAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.supportedProperties = devicesCapabilities.getSensorSupportedProperties();
			self.supportedWatchdogIntervals = devicesCapabilities.getSensorSupportedWatchdogIntervals();
			self.supportedInterfaces = devicesCapabilities.getSensorSupportedInterfaces();
		},function() {
		})
	}
	
	self.installMQttInterfaceTransductor = function(transductor) {
		return SensorAPIService.installMQttInterfaceSensor(transductor);
	}
	
	fetchSensorCapabilities();

});
