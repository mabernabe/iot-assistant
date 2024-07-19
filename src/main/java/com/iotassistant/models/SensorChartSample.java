package com.iotassistant.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chartsample")
public class SensorChartSample {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String value;
	
	private String date;
	


	public SensorChartSample() {
		super();
	}

	SensorChartSample(String value, String date) {
		super();
		this.value = value;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public String getDate() {
		return date;
	}



	
	
	

}
