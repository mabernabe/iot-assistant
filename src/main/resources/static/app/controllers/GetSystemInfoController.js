var getSystemInfoController= angular.module('getSystemInfoController', ['stationAPIService']);


getSystemInfoController.controller("GetSystemInfoController",function($interval, $scope, StationAPIService){

	const SYSTEM_INFO_REFRESH_TIME_MS = 5000 ;	

	var self = this;

	self.station = null;
	
	self.dataIsReady = false;

	self.getStation = function(){
		StationAPIService.getStation()
		.then(function(station) { 
			self.station = station;	
		},function() {
			self.station = null;
		})
	}
	
	var refreshStationInterval = $interval(self.getStation, SYSTEM_INFO_REFRESH_TIME_MS);
	
	$scope.$on('$destroy',function(){
		if (refreshStationInterval ) {
			$interval.cancel(refreshStationInterval );
		}
	})

	self.getStation();
	

});
