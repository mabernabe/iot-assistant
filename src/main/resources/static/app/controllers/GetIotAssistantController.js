var getIotAssistantController= angular.module('getIotAssistantController', ['iotAssistantAPIService']);


getIotAssistantController.controller("GetIotAssistantController",function($interval, $scope, IotAssistantAPIService){

	const REFRESH_IOTASSISTANT_TIME_INTERVAL_MS = 5000 ;	

	var self = this;

	self.iotAssistant = null;
	
	self.dataIsReady = false;

	self.getIotAssistant = function(){
		IotAssistantAPIService.getIotAssistant()
		.then(function(iotAssistant) { 
			self.iotAssistant = iotAssistant;	
		},function() {
			self.iotAssistant = null;
		})
	}
	
	var refreshIotAssistantInterval = $interval(self.getIotAssistant, REFRESH_IOTASSISTANT_TIME_INTERVAL_MS);
	
	$scope.$on('$destroy',function(){
		if (refreshIotAssistantInterval ) {
			$interval.cancel(refreshIotAssistantInterval );
		}
	})

	self.getIotAssistant();
	

});
