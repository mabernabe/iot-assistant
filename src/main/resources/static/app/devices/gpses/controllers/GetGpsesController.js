
gpsesModule.controller("GetGpsesController",function($scope, GpsAPIService, $interval, SweetAlertService){

	const FETCH_GPSS_REFRESH_TIME_MS = 10000 ;

	let self = this;

	self.gpses = [];

	let fetchGpses = function(){
		GpsAPIService.getGPSes()
		.then(function(gpses) { 
			self.gpses = gpses;	
		},function() {
			self.gpses = [];
		})
	}
	
	let initializeController = function() {
		fetchGpses();   
		let fetchGpsInterval = $interval(fetchGpses, FETCH_GPSS_REFRESH_TIME_MS);
		$scope.$on('$destroy',function(){
			if(fetchGpsInterval) {
				$interval.cancel(fetchGpsInterval);
			}
		})
	}
	
	initializeController(); 
	
	self.deleteGps = function(gpsName){
		function deleteGps() {
			GpsAPIService.deleteGps(gpsName)
			.then(function() { 
				SweetAlertService.showSuccessAlert('GPS deleted with success');
			},function() {
				SweetAlertService.showErrorAlert('GPS deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete gps ' + gpsName + '?', deleteGps);
	}
	
	self.enableWatchdog = function(enable, cameraName){
		enableString = (enable ? 'enabled' : 'disabled') ;
		GpsAPIService.enableWatchdog(enable, cameraName)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Watchdog ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Watchdog could not be ' + enableString);
		})
	}

});

