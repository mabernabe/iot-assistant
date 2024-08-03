package com.iotassistant.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.iotassistant.models.devices.transductors.Sensor;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;

@Entity
@DiscriminatorValue("chart")
public class SensorChart {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@NotNull
	private String sensorName;
	
	@Enumerated(EnumType.STRING)
	private PropertyMeasuredEnum propertyObserved;
	
	@Enumerated(EnumType.STRING)
	private SensorChartIntervalEnum totalInterval;
	
	@Enumerated(EnumType.STRING)
	private SensorChartSampleIntervalEnum sampleInterval;
	
	@Enumerated(EnumType.STRING)
	private SensorChartTypeEnum type;
	
	private @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade=CascadeType.ALL)
	List<SensorChartSample> sensorsChartSamples = new ArrayList<SensorChartSample>();
	
	

	public SensorChart() {
		super();
	}

	public SensorChart(String sensorName, PropertyMeasuredEnum propertyObserved, SensorChartIntervalEnum totalInterval, 
			SensorChartSampleIntervalEnum sampleInterval, SensorChartTypeEnum type) {
		super();
		this.sensorName = sensorName;
		this.propertyObserved = propertyObserved;
		this.totalInterval = totalInterval;
		this.sampleInterval = sampleInterval;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}


	public String getSensorName() {
		return sensorName;
	}

	public PropertyMeasuredEnum getPropertyObserved() {
		return propertyObserved;
	}


	public SensorChartSampleIntervalEnum getSampleInterval() {
		return sampleInterval;
	}


	public SensorChartIntervalEnum getTotalInterval() {
		return totalInterval;
	}

	public List<SensorChartSample> getSensorsChartSamples() {
		return sensorsChartSamples;
	}

	public boolean shouldAddNewSample(Sensor sensor) {
		assert(sensor.isActive());
		if (!this.sensorName.equals(sensor.getName())) {
			return false;
		}
		if (this.getLastSample() == null) {
			return true;
		}
		try {
			boolean isSampleintervalReached = sampleInterval.isSampleIntervalReached(this.getLastSample().getDate(), sensor.getLastValueDate());
			return this.propertyObserved.isBinary() && !this.getLastSample().getValue().equals(sensor.getValue(this.propertyObserved))
					|| isSampleintervalReached;
		} catch (ParseException e) {
			assert(true);
			return false;
		} 
	}
	
	public void addNewSensorSample(Sensor sensor) {
		assert(this.shouldAddNewSample(sensor) == true);
		SensorChartSample sample = new SensorChartSample(sensor.getValues().getValue(propertyObserved), sensor.getValues().getDate());
		this.addSample(sample);
		deleteSamplesIfTotalIntervalReached(sample);
						
	}


	private SensorChartSample getLastSample() {
		if (sensorsChartSamples.size() == 0) {
			return null;
		}
		else {
			return sensorsChartSamples.get(sensorsChartSamples.size() - 1);
		}
	}
	
	
	private void deleteSamplesIfTotalIntervalReached(SensorChartSample lastSample) {
		Iterator<SensorChartSample> samplesIterator = sensorsChartSamples.iterator();
		while (samplesIterator.hasNext()) {
			SensorChartSample sample = samplesIterator.next();
			try {
				if (totalInterval.isTotalIntervalReached (sample.getDate(), lastSample.getDate()) ) {
					samplesIterator.remove();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	


	public SensorChartTypeEnum getType() {
		return type;
	}

	private void addSample(SensorChartSample newSensorSample) {
		sensorsChartSamples.add(newSensorSample);
		
	}

	

}
