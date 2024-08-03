package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.devices.transductors.Actuator;

public class ActuatorsDTO {
	
	private ArrayList<ActuatorDTO> actuators = new ArrayList<ActuatorDTO>();
	
	public ActuatorsDTO() {};

	public ActuatorsDTO(List<Actuator> actuators) {
		for (int i = 0; i < actuators.size(); i++) {
			this.actuators.add(new ActuatorDTO(actuators.get(i)));
		}
	}

	public ArrayList<ActuatorDTO> getActuators() {
		return actuators;
	}

	
}
