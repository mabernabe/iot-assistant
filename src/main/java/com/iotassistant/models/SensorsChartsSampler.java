package com.iotassistant.models;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iotassistant.models.devices.Sensor;
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
			Sensor sensor = sensorsService.getSensorByName(chart.getSensorName());
			boolean shouldAddNewSample = chart.shouldAddNewSample(sensor);
			if (shouldAddNewSample) {
				chart.addNewSensorSample(sensor);
				chartService.updateChart(chart);
			}				
		}
	}

}
