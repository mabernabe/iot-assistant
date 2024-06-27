package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.controllers.dtos.TransductorDTO;
import com.iotassistant.models.transductor.Sensor;

public class SensorDTO extends TransductorDTO{
	
	private SensorValuesDTO sensorValues;

	public SensorDTO(Sensor sensor) {
		super(sensor);
		this.sensorValues = sensor.isActive() ? new SensorValuesDTO(sensor.getValues()) : null;
	}

	public SensorValuesDTO getSensorValues() {
		return sensorValues;
	}

	


	

}
