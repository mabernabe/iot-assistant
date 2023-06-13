package com.iotassistant.models;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iotassistant.services.ChartsService;
import com.iotassistant.services.SensorsService;

@Component
public class SensorsChartsSampler {
	
	@Autowired
	ChartsService chartService;
	
	@Autowired
	SensorsService sensorsService;

	
	@Scheduled(fixedRate = 60000)
	public void fillSensorsChartsData() {
		List<SensorChart > charts = chartService.getAllCharts();
		for (SensorChart chart: charts) {
			boolean shouldAddNewSample = chart.shouldAddNewSample(sensorsService);
			if (shouldAddNewSample) {
				chart.addNewSensorSample(sensorsService);
				chartService.updateChart(chart);
			}				
		}
	}

}
