var installCameraController= angular.module('installCameraController', ['iotAssistantAPIService', 'cameraAPIService', 'sweetAlertService']);


installCameraController.controller("InstallCameraController",function(IotAssistantAPIService, CameraAPIService, SweetAlertService, $route){

	var self = this;
	
	self.camera = new HTTPCamera();
	
	self.cameraCapabilities = new CameraCapabilities();
	
	var initializeController = function() {
		getCameraCapabilities();
	}
	
	var getCameraCapabilities = function(){
		IotAssistantAPIService.getCapabilities()
		.then(function(cameraCapabilities) { 
			self.cameraCapabilities = cameraCapabilities.getCameraCapabilities();
		},function() {
		})
	}
	
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
			var redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Camera installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Camera installation failed' + ' \n Error: ' + error.data.message);
		})
	}
	
	initializeController();

});
