package com.iotassistant.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iotassistant.models.transductor.SensorMeasure;

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

	public SensorChartSample(SensorMeasure measure) {
		super();
		this.value = measure.getValue();
		this.date = measure.getDate();
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
