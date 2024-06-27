package com.iotassistant.controllers.dtos.transductor;

import java.util.ArrayList;

import com.iotassistant.controllers.dtos.TransductorDTO;
import com.iotassistant.models.transductor.Actuator;

public class ActuatorDTO extends TransductorDTO{
	
	private ArrayList<ActuatorValueDTO> values = new ArrayList<>();
	

	public ActuatorDTO(Actuator actuator) {
		super(actuator);
		
	}


	public ArrayList<ActuatorValueDTO> getValues() {
		return values;
	}
	

}
