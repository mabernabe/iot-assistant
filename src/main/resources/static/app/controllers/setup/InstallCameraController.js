var installCameraController= angular.module('installCameraController', ['stationAPIService', 'cameraAPIService', 'sweetAlertService']);


installCameraController.controller("InstallCameraController",function(StationAPIService, CameraAPIService, SweetAlertService, $route){

	var self = this;
	
	self.camera = new HTTPCamera();
	
	self.stationCameraCapabilities = new StationCameraCapabilities();
	
	var initializeController = function() {
		getStationCameraCapabilities();
	}
	
	var getStationCameraCapabilities = function(){
		StationAPIService.getCapabilities()
		.then(function(stationCapabilities) { 
			self.stationCameraCapabilities = stationCapabilities.getCameraCapabilities();
		},function() {
		})
	}
	
	self.getSupportedInterfaces = function() {
		return self.stationCameraCapabilities.getSupportedInterfaces();
	}
	
	self.getSupportedWatchdogIntervals = function() {
		return self.stationCameraCapabilities.getSupportedWatchdogIntervals();
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
