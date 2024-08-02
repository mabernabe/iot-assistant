
sensorsModule.controller ("InstallSensorController",function($scope, $controller, SensorAPIService){

	let self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Sensor";
	
	let fetchSensorCapabilities = function(){
		self.systemAPIService.getSensorsCapabilities()
		.then(function(sensorsCapabilities) { 
			self.supportedProperties = sensorsCapabilities.getSupportedProperties();
			self.supportedWatchdogIntervals = sensorsCapabilities.getSupportedWatchdogIntervals();
			self.supportedInterfaces = sensorsCapabilities.getSupportedInterfaces();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchSensorCapabilities();
	}
	
	initializeController();
	
	self.installMQttInterfaceTransductor = function(transductor) {
		return SensorAPIService.installMQttInterfaceSensor(transductor);
	}

});
