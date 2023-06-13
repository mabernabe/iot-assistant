var chartsDrawService = angular.module('chartsDrawService',  []);

chartsDrawService.service('ChartsDrawService', function ($location, $route) {
    var self = this;	
    
    self.chartsColor = "#f17e5d";
    
    
	self.drawLineChartWithPoints = function (domElement, xData, yData, label) {		
        return new Chart(domElement, {
        	type: 'line',
     		data: {
        		labels: xData,
        	datasets: [{
				label: label,
          		borderColor: self.chartsColor,
          		pointBackgroundColor: self.chartsColor,
          		pointRadius: 3,
          		pointHoverRadius: 3,
          		lineTension: 0,
          		fill: false,
          		borderWidth: 3,
          		data: yData
        	}]
      		},
      		options: {
        		legend: {
          		display: false
        	},
        	tooltips: {
          		enabled: true
        	},
        	scales: {
          		yAxes: [{
            		ticks: {
              			fontColor: "#9f9f9f",
              			beginAtZero: false,
              			maxTicksLimit: 5,
            		},
            		gridLines: {
              			drawBorder: false,
              			borderDash: [8, 5],
              			zeroLineColor: "transparent",
              			color: '#9f9f9f'
            		}
          		}],
          		xAxes: [{
            		barPercentage: 1.6,
            		gridLines: {
              			drawBorder: false,
              			borderDash: [8, 5],
              			color: '#9f9f9f',
              			zeroLineColor: "transparent"
            		},
            		ticks: {
              			padding: 20,
              			fontColor: "#9f9f9f"
            			}
          			}]
        		},
      		}
    	});
	}
	

	
	
	
});
