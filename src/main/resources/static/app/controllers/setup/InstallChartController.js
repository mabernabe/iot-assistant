var installChartController= angular.module('installChartController', ['stationAPIService', 'sensorAPIService', 'sweetAlertService', 'chartAPIService']);


installChartController.controller("InstallChartController",function(StationAPIService, SensorAPIService, ChartAPIService, SweetAlertService, $route){

	var self = this;
	
	self.chart = new SensorChart();
	
	self.sensors = [];
	
	self.stationSupportedChartTypes = [];
	
	self.stationSupportedChartIntervals = [];
	
	self.sensorPropertiesOptions = [];


	var getSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;
		},function() {
			self.sensors = [];
		})
	}
	
	var getStationCapabilities = function(){
		StationAPIService.getCapabilities()
		.then(function(stationCapabilities) { 
			self.stationSupportedChartTypes = stationCapabilities.getSupportedChartTypes();
			self.stationSupportedChartIntervals = stationCapabilities.getSupportedChartIntervals();
			self.stationSupportedSampleIntervals = stationCapabilities.getSupportedSampleIntervals();
		},function() {
		})
	}
	
	self.updateSensorPropertiesOptions = function(selectedSensorName){
		self.sensors.forEach(sensor  => {
			if (sensor.getName() == selectedSensorName) {
				self.sensorPropertiesOptions = sensor.getProperties();
			}
		})
	}
	
	self.setChartType = function(chartType) {
		self.chart.setType(chartType);
	}
	
	self.allRequired = function() {
		return (self.chart.isValid());
	}


	self.installAndRedirect = function() {
		ChartAPIService.installChart(self.chart).then(function() {
			var redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Chart installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Chart installation failed' + ' \n Error: ' + error.data.message);
		})
	}
	
	getSensors(); 
	
	getStationCapabilities();

});
