var getChartsController= angular.module('getChartsController', ['chartAPIService', 'sweetAlertService', 'chartsDrawService']);


getChartsController.controller("GetChartsController",function($scope, $timeout, ChartAPIService, SweetAlertService, ChartsDrawService, $interval){

	const CHARTS_REFRESH_TIME_MS = 5000 ;
	
	const CHARTS_REDRAW_REFRESH_TIME_MS = 120000 ;

	var self = this;

	self.charts = [];
	
	self.chartsForNgRepeat = []; // This is weird but chartist does not work using ng-repeat with original objects

	self.getNgRepeatCharts = function(charts) {
		chartsCopy = [];
		for (var i = 0; i < charts.length; i++) {
			chartsCopy.push(charts[i].getId() + ":" + charts[i].getSensorName() + ":" + charts[i].getSensorPropertyName());
		}
		return chartsCopy;
	}
	
	self.getIdFromNgRepeatChart = function(chart) {
    	var array = chart.split(':');
    	return array[0];
	}
	
	self.getSensorNameFromNgRepeatChart = function(chart) {
    	var array = chart.split(':');
    	return array[1];
	}
	
	self.getSensorPropertyNameFromNgRepeatChart = function(chart) {
    	var array = chart.split(':');
    	return array[2];
	}

	self.getCharts = function(drawCharts){
		ChartAPIService.getCharts()
		.then(function(charts) { 
			self.charts = charts;
			self.chartsForNgRepeat = self.getNgRepeatCharts(charts);
			if(drawCharts) {
				$timeout(function(){
 					self.drawCharts(charts);
				},0,false);	
			}		
		},function() {
			self.charts = [];
		})
	}
	
	self.drawCharts = function(){
		for (var i = 0; i < self.charts.length; i++) {
			var chart = self.charts[i];
			var chartElementId = document.getElementById(chart.getId()).getContext("2d");
			ChartsDrawService.drawLineChartWithPoints(chartElementId, chart.getXData(), chart.getYData(), chart.getSensorPropertyName());
		}
	}
	
	self.getCharts(true);   
	
	var refreshChartsInterval = $interval(function() { self.getCharts(false); }, CHARTS_REFRESH_TIME_MS);
	
	var redrawChartsInterval = $interval(self.drawCharts, CHARTS_REDRAW_REFRESH_TIME_MS);
	
	$scope.$on('$destroy',function(){
		if(refreshChartsInterval) {
			$interval.cancel(refreshChartsInterval);
		}
		if(redrawChartsInterval) {
			$interval.cancel(redrawChartsInterval
			);
		}
	})

	self.deleteChart = function(chartId){
		function deleteChart() {
			ChartAPIService.deleteChart(chartId)
			.then(function() { 
				SweetAlertService.showSuccessAlert('Chart deleted');
			},function() {
				SweetAlertService.showErrorAlert('Chart deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete chart ' + chartId + '?', deleteChart);
	}
	
	

});
