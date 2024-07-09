
devicesModule.controller ("InstallSensorController",function($scope, $controller, SensorAPIService){

	let self = this;
	
	angular.extend(this, $controller('InstallTransductorController', {$scope: $scope}));

	self.transductorType = "Sensor";
	
	let fetchSensorCapabilities = function(){
		self.iotAssistantAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.supportedProperties = devicesCapabilities.getSensorSupportedProperties();
			self.supportedWatchdogIntervals = devicesCapabilities.getSensorSupportedWatchdogIntervals();
			self.supportedInterfaces = devicesCapabilities.getSensorSupportedInterfaces();
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
