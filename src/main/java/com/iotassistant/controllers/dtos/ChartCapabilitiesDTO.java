package com.iotassistant.controllers.dtos;

import java.util.List;

public class ChartCapabilitiesDTO {
	
	private List<String> supportedChartTypes;
	
	private List<String> supportedChartIntervals;
	
	private List<String> supportedSampleIntervals;

	public ChartCapabilitiesDTO(List<String> supportedChartTypes, List<String> supportedChartIntervals,
			List<String> supportedSampleIntervals) {
		super();
		this.supportedChartTypes = supportedChartTypes;
		this.supportedChartIntervals = supportedChartIntervals;
		this.supportedSampleIntervals = supportedSampleIntervals;
	}

	public List<String> getSupportedChartTypes() {
		return supportedChartTypes;
	}

	public List<String> getSupportedChartIntervals() {
		return supportedChartIntervals;
	}

	public List<String> getSupportedSampleIntervals() {
		return supportedSampleIntervals;
	}
	
	

}
