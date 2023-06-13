package com.iotassistant.controllers.dtos.transductor;

import java.util.ArrayList;

import com.iotassistant.controllers.dtos.TransductorDTO;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.ActuatorValue;
import com.iotassistant.models.transductor.TransductorInterfaceException;

public class ActuatorDTO extends TransductorDTO{
	
	private ArrayList<ActuatorValueDTO> values = new ArrayList<>();
	

	public ActuatorDTO(Actuator actuator) {
		super(actuator);
		try {
			for (ActuatorValue actuatorValue: actuator.getValues()) {
				values.add(new ActuatorValueDTO(actuatorValue));
			}
		} catch (TransductorInterfaceException e) {
		}
	}


	public ArrayList<ActuatorValueDTO> getValues() {
		return values;
	}
	

}
