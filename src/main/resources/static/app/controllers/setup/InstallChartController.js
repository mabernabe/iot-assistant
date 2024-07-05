var installChartController= angular.module('installChartController', ['iotAssistantAPIService', 'sensorAPIService', 'sweetAlertService', 'chartAPIService']);


installChartController.controller("InstallChartController",function(IotAssistantAPIService, SensorAPIService, ChartAPIService, SweetAlertService, $route){

	var self = this;
	
	self.chart = new SensorChart();
	
	self.sensors = [];
	
	self.supportedChartTypes = [];
	
	self.supportedChartIntervals = [];
	
	self.sensorPropertiesOptions = [];


	var getSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;
		},function() {
			self.sensors = [];
		})
	}
	
	var getChartCapabilities = function(){
		IotAssistantAPIService.getChartCapabilities()
		.then(function(chartCapabilities) { 
			self.supportedChartTypes = chartCapabilities.getSupportedChartTypes();
			self.supportedChartIntervals = chartCapabilities.getSupportedChartIntervals();
			self.supportedSampleIntervals = chartCapabilities.getSupportedSampleIntervals();
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
	
	getIotAssistantCapabilities();

});
