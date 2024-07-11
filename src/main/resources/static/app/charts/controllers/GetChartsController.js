
chartsModule.controller("GetChartsController",function($scope, $timeout, ChartAPIService, SweetAlertService, ChartsDrawService, $interval){

	const FETCH_CHARTS_REFRESH_TIME_MS = 5000 ;
	
	const CHARTS_REDRAW_REFRESH_TIME_MS = 120000 ;

	let self = this;

	self.charts = [];
	
	self.chartsForNgRepeat = []; // This is weird but chartist does not work using ng-repeat with original objects

	self.getNgRepeatCharts = function(charts) {
		chartsCopy = [];
		for (let i = 0; i < charts.length; i++) {
			chartsCopy.push(charts[i].getId() + ":" + charts[i].getSensorName() + ":" + charts[i].getSensorPropertyName());
		}
		return chartsCopy;
	}
	
	self.getIdFromNgRepeatChart = function(chart) {
    	let array = chart.split(':');
    	return array[0];
	}
	
	self.getSensorNameFromNgRepeatChart = function(chart) {
    	let array = chart.split(':');
    	return array[1];
	}
	
	self.getSensorPropertyNameFromNgRepeatChart = function(chart) {
    	let array = chart.split(':');
    	return array[2];
	}

	self.fetchCharts = function(drawCharts){
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
		for (let i = 0; i < self.charts.length; i++) {
			let chart = self.charts[i];
			let chartElementId = document.getElementById(chart.getId()).getContext("2d");
			ChartsDrawService.drawLineChartWithPoints(chartElementId, chart.getXData(), chart.getYData(), chart.getSensorPropertyName());
		}
	}
	
	let initializeController = function() {
		self.fetchCharts(true);
	}
	
	initializeController();  
	
	let refreshChartsInterval = $interval(function() { self.fetchCharts(false); }, FETCH_CHARTS_REFRESH_TIME_MS);
	
	let redrawChartsInterval = $interval(self.drawCharts, CHARTS_REDRAW_REFRESH_TIME_MS);
	
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
