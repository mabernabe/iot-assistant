package com.iotassistant.controllers.dtos;

import com.iotassistant.models.SensorChartSample;

class SensorChartSampleDTO {
	
	 private Integer id;
		
	 private String date;
	 
	 private String value;
	 

	SensorChartSampleDTO(SensorChartSample chartSample) {
		this.id = chartSample.getId();
		this.date = chartSample.getDate();
		this.value = chartSample.getValue();
	}

	public Integer getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getValue() {
		return value;
	}

	
	 
	 

}
