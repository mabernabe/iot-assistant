
camerasModule.controller("InstallCameraController",function(SystemAPIService, CameraAPIService, SweetAlertService, $route){

	let self = this;
	
	self.camera = new HTTPCamera();
	
	self.cameraCapabilities = new CameraCapabilities();
	
	let fetchCameraCapabilities = function(){
		SystemAPIService.getCapabilities()
		.then(function(cameraCapabilities) { 
			self.cameraCapabilities = cameraCapabilities.getCameraCapabilities();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchCameraCapabilities();
	}
	
	initializeController();
	
	self.getSupportedInterfaces = function() {
		return self.cameraCapabilities.getSupportedInterfaces();
	}
	
	self.getSupportedWatchdogIntervals = function() {
		return self.cameraCapabilities.getSupportedWatchdogIntervals();
	}
	
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
