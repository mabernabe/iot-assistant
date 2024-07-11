
chartsModule.controller("InstallChartController",function(IotAssistantAPIService, SensorAPIService, ChartAPIService, SweetAlertService, $route){

	let self = this;
	
	self.chart = new SensorChart();
	
	self.sensors = [];
	
	self.supportedChartTypes = [];
	
	self.supportedChartIntervals = [];
	
	self.sensorPropertiesOptions = [];

	let fetchSensors = function(){
		SensorAPIService.getSensors()
		.then(function(sensors) { 
			self.sensors = sensors;
		},function() {
			self.sensors = [];
		})
	}
	
	let fetchChartCapabilities = function(){
		IotAssistantAPIService.getChartCapabilities()
		.then(function(chartCapabilities) { 
			self.supportedChartTypes = chartCapabilities.getSupportedChartTypes();
			self.supportedChartIntervals = chartCapabilities.getSupportedChartIntervals();
			self.supportedSampleIntervals = chartCapabilities.getSupportedSampleIntervals();
		},function() {
		})
	}
	
	let initializeController = function() {
		fetchSensors();
		fetchChartCapabilities();
	}
	
	initializeController(); 
	
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
			let redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect('Chart installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert('Chart installation failed' + ' \n Error: ' + error.data.message);
		})
	}

});
