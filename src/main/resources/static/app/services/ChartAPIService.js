var chartAPIService = angular.module('chartAPIService', ['restAPIService']);

chartAPIService.service ("ChartAPIService",function(RestAPIService, $q){
	
	var self = this;	
	
	var chartsBaseUri = "charts/";

	self.getCharts = function () {
		var deferred = $q.defer();
		RestAPIService.get(chartsBaseUri).then(function(objectResponse) {
			deferred.resolve(getChartsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

	function getChartsFromResponse(objectResponse) {
		var charts = [];
		objectResponse.charts.forEach(chartObject => {
			var chart = getChartFromObjectResponse(chartObject);
			charts.push(chart);
		})
		return charts;
	}
	
	function getChartFromObjectResponse(chartObject) {
		var chart = new SensorChart();
		chart.setSensorName(chartObject.sensorName);
		var property = new Property(chartObject.propertyObserved.name, chartObject.propertyObserved.shortName, chartObject.propertyObserved.digital, chartObject.propertyObserved.minimumValue, chartObject.propertyObserved.maximumValue);
		chart.setSensorProperty(property);
		chart.setType(chartObject.type);
		chart.setId(chartObject.id);
		chart.setTotalInterval(chartObject.totalInterval);
		chart.setSampleInterval(chartObject.sampleInterval);
		var sensorChartSamples = [];
		chartObject.sensorsChartSamples.forEach(sample => {
			var sample = new SensorChartSample(sample.value, sample.date);
			sensorChartSamples.push(sample);
		})
		chart.setSensorChartSamples(sensorChartSamples); 
		return chart;
	}
	
	
	self.installChart = function (chart) {
		var deferred = $q.defer();
		var newChart = createNewChartObjRequest(chart);
		RestAPIService.post(chartsBaseUri, newChart).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewChartObjRequest(chart) {
		var newChartObject = {};
		newChartObject.sensorName = chart.getSensorName();
		newChartObject.propertyObserved = chart.getSensorProperty();
		newChartObject.type = chart.getType();
		newChartObject.totalInterval = chart.getTotalInterval();
		newChartObject.sampleInterval = chart.getSampleInterval();
		return newChartObject;
	}
	
	self.deleteChart = function (chartId) {
		var deferred = $q.defer();
		RestAPIService.delete(chartsBaseUri.concat(chartId)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	
	

});

