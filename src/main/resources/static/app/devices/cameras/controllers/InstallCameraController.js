
camerasModule.controller("InstallCameraController",function(SystemAPIService, CameraAPIService, SweetAlertService, $route){

	let self = this;
	
	self.camera = new HTTPCamera();
	
	let fetchCameraCapabilities = function(){
		SystemAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.supportedInterfaces = devicesCapabilities.getCameraSupportedInterfaces();
			self.supportedWatchdogIntervals = devicesCapabilities.getCameraSupportedWatchdogIntervals();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchCameraCapabilities();
	}
	
	initializeController();
	
	self.setCameraInterfaceType = function(interfaceType) {
		self.camera.setInterfaceType(interfaceType);
	}
	
	self.allRequired = function() {
		return (self.camera.isValid());
	}

	self.installAndRedirect = function() {
		CameraAPIService.installHTTPCamera(self.camera).then(function() {
			let redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Camera installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Camera installation failed' + ' \n Error: ' + error.data.message);
		})
	}

});
