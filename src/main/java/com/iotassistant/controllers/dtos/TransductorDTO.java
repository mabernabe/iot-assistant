package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.Transductor;

public abstract class TransductorDTO extends DeviceDTO{
	
	private List<PropertyDTO> properties = new ArrayList<>();

	public TransductorDTO(Transductor transductor) {
		super(transductor);
		for (Property property: transductor.getProperties()) {
			properties.add(new PropertyDTO(property));
		}
	}

	public List<PropertyDTO> getProperties() {
		return properties;
	}

}
