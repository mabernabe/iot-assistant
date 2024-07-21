
systemModule.controller("GetSystemInfoController",function($interval, $scope, SystemAPIService){

	const REFRESH_SYSTEM_TIME_INTERVAL_MS = 5000 ;	

	let self = this;

	self.system = null;

	let fetchSystem = function(){
		SystemAPIService.getSystem()
		.then(function(system) { 
			self.system = system;	
		},function() {
			self.system = null;
		})
	}
	
	let initializeController = function() {
		fetchSystem();   
		let refreshSystemInterval = $interval(fetchSystem, REFRESH_SYSTEM_TIME_INTERVAL_MS);
		$scope.$on('$destroy',function(){
			if(refreshSystemInterval) {
				$interval.cancel(refreshSystemInterval);
			}
		})
	}

	initializeController(); 

});
