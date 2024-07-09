let getIotAssistantController= angular.module('getIotAssistantController', ['iotAssistantAPIService']);


getIotAssistantController.controller("GetIotAssistantController",function($interval, $scope, IotAssistantAPIService){

	const REFRESH_IOTASSISTANT_TIME_INTERVAL_MS = 5000 ;	

	let self = this;

	self.iotAssistant = null;
	
	self.dataIsReady = false;

	let fetchIotAssistant = function(){
		IotAssistantAPIService.getIotAssistant()
		.then(function(iotAssistant) { 
			self.iotAssistant = iotAssistant;	
		},function() {
			self.iotAssistant = null;
		})
	}
	
	let initializeController = function() {
		fetchIotAssistant();   
		let refreshIotAssistantInterval = $interval(fetchIotAssistant, REFRESH_IOTASSISTANT_TIME_INTERVAL_MS);
		$scope.$on('$destroy',function(){
			if(refreshIotAssistantInterval) {
				$interval.cancel(refreshIotAssistantInterval);
			}
		})
	}

	initializeController(); 

});
