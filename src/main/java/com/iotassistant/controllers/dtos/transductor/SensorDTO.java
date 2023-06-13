package com.iotassistant.controllers.dtos.transductor;

import java.util.ArrayList;

import com.iotassistant.models.transductor.SensorMeasure;
import com.iotassistant.controllers.dtos.TransductorDTO;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.TransductorInterfaceException;

public class SensorDTO extends TransductorDTO{
	
	private ArrayList<MeasureDTO> measures = new ArrayList<>();

	public SensorDTO(Sensor sensor) {
		super(sensor);
		try {
			for (SensorMeasure measure: sensor.getMeasures()) {
				measures.add(new MeasureDTO(measure));
			}
		} catch (TransductorInterfaceException e) {
			System.out.println("get Measures transductor interface exception");
		}
	}

	public ArrayList<MeasureDTO> getMeasures() {
		return measures;
	}

	


	

}
