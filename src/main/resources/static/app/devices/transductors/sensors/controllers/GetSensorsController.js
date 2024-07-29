
sensorsModule.controller ("GetSensorsController",function($scope, SensorAPIService, $interval, SweetAlertService){

	const FETCH_SENSORS_REFRESH_TIME_MS = 2000 ;

	var self = this;

	self.sensors = [];

	let fetchSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;	
		},function() {
			self.sensors = [];
		})
	}
	
	let initializeController = function() {
		fetchSensors();   
		let fetchSensorsInterval = $interval(fetchSensors, FETCH_SENSORS_REFRESH_TIME_MS);
		$scope.$on('$destroy',function(){
			if(fetchSensorsInterval) {
				$interval.cancel(fetchSensorsInterval);
			}
		})
	}

	initializeController();		
	
	self.deleteSensor = function(sensorName){
		function deleteSensor() {
			SensorAPIService.deleteSensor(sensorName)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Sensor deleted with success');
			},function() {
				SweetAlertService.showErrorAlert('Sensor deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete sensor ' + sensorName + '?', deleteSensor);
	}
	
	self.enableWatchdog = function(enable, sensorName){
		enableString = (enable ? 'enabled' : 'disabled') ;
		SensorAPIService.enableWatchdog(enable, sensorName)
		.then(function() { 
			SweetAlertService.showSuccessAlert('Watchdog ' + enableString + ' with success');
		},function() {
			SweetAlertService.showErrorAlert('Watchdog could not be ' + enableString);
		})
	} 

});