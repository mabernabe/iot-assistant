package com.iotassistant.controllers.dtos.transductor;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.transductor.Sensor;

public class SensorsDTO {
	
	private ArrayList<SensorDTO> sensors = new ArrayList<SensorDTO>();
	
	public SensorsDTO() {};

	public SensorsDTO(List<Sensor> sensors) {
		for (int i = 0; i < sensors.size(); i++) {
			this.sensors.add(new SensorDTO(sensors.get(i)));
		}
	}

	public ArrayList<SensorDTO> getSensors() {
		return sensors;
	}

	public void setSensors(ArrayList<SensorDTO> sensors) {
		this.sensors = sensors;
	}
	
	

}
