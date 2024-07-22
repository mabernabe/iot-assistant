package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.devices.Sensor;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;

public class SensorDTO extends TransductorDTO{
	
	private SensorValuesDTO sensorValues;
	
	private List<PropertyMeasuredDTO> propertiesMeasured;

	public SensorDTO(Sensor sensor) {
		super(sensor);
		this.sensorValues = sensor.isActive() ? new SensorValuesDTO(sensor.getValues()) : null;
		this.propertiesMeasured = new ArrayList<PropertyMeasuredDTO>();
		for (PropertyMeasuredEnum propertyMeasured : sensor.getPropertiesMeasured()) {
			this.propertiesMeasured.add(new PropertyMeasuredDTO(propertyMeasured));
		}
	}

	public SensorValuesDTO getSensorValues() {
		return sensorValues;
	}

	public List<PropertyMeasuredDTO> getPropertiesMeasured() {
		return propertiesMeasured;
	}

}
