package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.SensorChart;
import com.iotassistant.models.SensorChartIntervalEnum;
import com.iotassistant.models.SensorChartSample;
import com.iotassistant.models.SensorChartSampleIntervalEnum;
import com.iotassistant.models.SensorChartTypeEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public class ChartDTO {

	private Integer id;
	
	private String sensorName;
	
	private PropertyDTO propertyObserved;
	
	private String totalInterval;
	
	private String sampleInterval;
	
	private String type;
	
	List<SensorChartSampleDTO> sensorsChartSamples; 
	

	public ChartDTO() {
		super();
	}

	public ChartDTO(SensorChart chart) {
		this.id = chart.getId();
		this.sensorName = chart.getSensorName();
		this.propertyObserved = new PropertyDTO(chart.getPropertyObserved());
		this.totalInterval = chart.getTotalInterval().toString();
		this.sampleInterval = chart.getSampleInterval().toString();
		this.sensorsChartSamples = new ArrayList<SensorChartSampleDTO>();
		for (SensorChartSample chartSample : chart.getSensorsChartSamples()) {
			this.sensorsChartSamples.add(new SensorChartSampleDTO(chartSample));
		}
		this.type = chart.getType().toString();
	}

	@JsonIgnore
	public SensorChart getChart() {
		PropertyMeasuredEnum propertyMeasuredEnum = PropertyMeasuredEnum.getInstance(propertyObserved.getName());
		SensorChartIntervalEnum totalIntervalEnum = SensorChartIntervalEnum.getInstance(totalInterval);
		SensorChartSampleIntervalEnum sampleIntervalEnum = SensorChartSampleIntervalEnum.getInstance(sampleInterval);
		SensorChartTypeEnum typeEnum = SensorChartTypeEnum.getInstance(type);
		SensorChart chart = new SensorChart(sensorName, propertyMeasuredEnum, totalIntervalEnum, sampleIntervalEnum, typeEnum);
		return chart;
	}

	public Integer getId() {
		return id;
	}

	public String getSensorName() {
		return sensorName;
	}


	public PropertyDTO getPropertyObserved() {
		return propertyObserved;
	}

	public String getTotalInterval() {
		return totalInterval;
	}

	public String getSampleInterval() {
		return sampleInterval;
	}

	public List<SensorChartSampleDTO> getSensorsChartSamples() {
		return sensorsChartSamples;
	}

	public String getType() {
		return type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public void setPropertyObserved(PropertyDTO propertyObserved) {
		this.propertyObserved = propertyObserved;
	}

	public void setTotalInterval(String totalInterval) {
		this.totalInterval = totalInterval;
	}

	public void setSampleInterval(String sampleInterval) {
		this.sampleInterval = sampleInterval;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSensorsChartSamples(List<SensorChartSampleDTO> sensorsChartSamples) {
		this.sensorsChartSamples = sensorsChartSamples;
	}
	
	

}
