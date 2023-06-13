var cameraAPIService = angular.module('cameraAPIService', ['restAPIService']);

cameraAPIService.service ("CameraAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var camerasBaseUri = "cameras/";

	self.getCameras = function () {
		var deferred = $q.defer();
		RestAPIService.get(camerasBaseUri).then(function(objectResponse) {
			deferred.resolve(getCamerasFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	function getCamerasFromResponse(objectResponse) {
		var cameras = [];
		objectResponse.cameras.forEach(cameraObject => {
			var camera = new Camera(cameraObject.name, cameraObject.description, cameraObject.watchdogInterval, cameraObject.watchdogEnabled, cameraObject.urlPicture);
			cameras.push(camera);
		})
		return cameras;
	}
	
	
	self.installHTTPCamera = function (httpCamera) {
		var deferred = $q.defer();
		var newHTTPCameraObjRequest= createNewHTTPCameraObjRequest(httpCamera);
		RestAPIService.post(camerasBaseUri.concat("httpCameras/"), newHTTPCameraObjRequest).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewHTTPCameraObjRequest(httpCamera) {
		var newHTTPCameraObjRequest = {};
		newHTTPCameraObjRequest.name = httpCamera.getName();
		newHTTPCameraObjRequest.description = httpCamera.getDescription();
		newHTTPCameraObjRequest.watchdogInterval = httpCamera.getWatchdogInterval();
		newHTTPCameraObjRequest.url = httpCamera.getURL();
		return newHTTPCameraObjRequest;
	}
	

	self.deleteCamera = function (name) {
		var deferred = $q.defer();
		RestAPIService.delete(camerasBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, actuatorName) {
		var deferred = $q.defer();
		var watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(actuatorsBaseUri.concat(actuatorName + '/watchdog'), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}


});

