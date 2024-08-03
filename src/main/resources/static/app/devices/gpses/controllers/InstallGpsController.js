
gpsesModule.controller("InstallGpsController",function(SystemAPIService, GpsAPIService, SweetAlertService, $route){

	let self = this;
	
	self.gps = new MqttGps();
	
	let fetchGpsCapabilities = function(){
		SystemAPIService.getGpsesCapabilities()
		.then(function(gpsCapabilities) { 
			self.supportedInterfaces = gpsCapabilities.getSupportedInterfaces();
			self.supportedWatchdogIntervals = gpsCapabilities.getSupportedWatchdogIntervals();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchGpsCapabilities();
	}
	
	initializeController();
	
	self.setGpsInterfaceType = function(interfaceType) {
		self.gps.setInterfaceType(interfaceType);
	}
	
	self.allRequired = function() {
		return (self.gps.isValid());
	}

	self.installAndRedirect = function() {
		let promise;
		if (self.gps.interfaceTypeIsMQTT()) {
			promise = GpsAPIService.installMqttGps(self.gps);
		}
		promise.then(function() {
			let redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('GPS installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('GPS installation failed' + ' \n Error: ' + error.data.message);
		})
	}

});
