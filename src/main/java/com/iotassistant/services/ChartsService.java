package com.iotassistant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.SensorChart;
import com.iotassistant.models.SensorChartIntervalEnum;
import com.iotassistant.models.SensorChartSampleIntervalEnum;
import com.iotassistant.models.SensorChartTypeEnum;
import com.iotassistant.repositories.ChartsJPARepository;

@Service
@Transactional
public class ChartsService {

	private @Autowired
	ChartsJPARepository chartsRepository;


	public List<SensorChart> getAllCharts() {
		return chartsRepository.findAll();
	}

	public void updateChart( SensorChart sensorChart) {
		chartsRepository.save(sensorChart);
	}

	public void newChart(SensorChart chart, String sensorName) {
		chartsRepository.save(chart);		
	}

	public void deleteChartById(int id) {
		chartsRepository.deleteById(id);		
	}

	public List<String> getSupportedChartTypes() {
		return SensorChartTypeEnum.getAvailableChartTypes();
	}

	public List<String> getSupportedChartIntervals() {
		return SensorChartIntervalEnum.getAvailableChartIntervals();
	}

	public List<String> getSupportedSampleIntervals() {
		return SensorChartSampleIntervalEnum.getAvailableSampleIntervals();
	}
	
	public boolean existChart(SensorChart chart) {
		return getChart(chart) != null;
	}

	private SensorChart getChart(SensorChart chart) {
		List<SensorChart> allCharts = chartsRepository.findAll();
		SensorChart chartRet = null;
		for (SensorChart installedchart : allCharts ) {
			if (installedchart.getPropertyObserved().equals(chart.getPropertyObserved()) &&
					installedchart.getSensorName().equals(chart.getSensorName())) {
				chartRet = installedchart;
			}
		}
		return chartRet;
	}

	public SensorChart getChartById(int id) {
		Optional<SensorChart> optionalSensorChart = chartsRepository.findById(id);
		if (optionalSensorChart.isPresent()) {
			return optionalSensorChart.get();
		}
		else {
			return null;
		}
	}

	void deleteChartsBySensorName(String sensorName) {
		List<SensorChart> allCharts = chartsRepository.findAll();
		for (SensorChart chart : allCharts ) {
			if (chart.getSensorName().equals(sensorName)) {
				deleteChartById(chart.getId());
			}
		}
		
	}




}
