var getCamerasController= angular.module('getCamerasController', ['cameraAPIService', 'sweetAlertService']);


getCamerasController.controller("GetCamerasController",function($scope, CameraAPIService, $interval, SweetAlertService){

	const CAMERAS_REFRESH_TIME_MS = 2000 ;

	var self = this;

	self.cameras = [];

	self.getCameras = function(){
		CameraAPIService.getCameras()
		.then(function(cameras) { 
			self.cameras = cameras;	
		},function() {
			self.cameras = [];
		})
	}
	
	self.deleteCamera = function(cameraName){
		function deleteCamera() {
			CameraAPIService.deleteCamera(cameraName)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Camera deleted');
			},function() {
				SweetAlertService.showErrorAlert('Camera deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete camera ' + cameraName + '?', deleteCamera);
	}
	
	self.enableWatchdog = function(enable, cameraName){
		enableString = (enable ? 'enabled' : 'disabled') ;
		CameraAPIService.enableWatchdog(enable, cameraName)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Watchdog ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Watchdog could not be ' + enableString);
		})
	}
	
	self.getCameras();   
	
	var intervalIstance = $interval(self.getCameras, CAMERAS_REFRESH_TIME_MS);

	$scope.$on('$destroy',function(){
		if(intervalIstance) {
			$interval.cancel(intervalIstance);
		}
	})

});

