package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.SensorChart;

public class ChartsDTO {
	
	private List<ChartDTO> charts;

	public ChartsDTO(List<SensorChart> allCharts) {
		charts = new ArrayList<ChartDTO>();
		for (SensorChart chart : allCharts) {
			charts.add(new ChartDTO(chart));
		}
	}

	public List<ChartDTO> getCharts() {
		return charts;
	}
	
	

}
