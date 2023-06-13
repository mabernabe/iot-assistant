package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.transductor.Property;

public class TransductorCapabilitiesDTO extends DeviceCapabilitiesDTO{

	private List<PropertyDTO> supportedProperties = new ArrayList<PropertyDTO>();
	
	public TransductorCapabilitiesDTO(List<Property> supportedSensorProperties, List<String> supportedInterfaces, List<String> supportedWatchdogIntervals) {
		super(supportedInterfaces, supportedWatchdogIntervals);
		for (Property property: supportedSensorProperties) {
			supportedProperties.add(new PropertyDTO(property));
		}
	}

	public List<PropertyDTO> getSupportedProperties() {
		return supportedProperties;
	}
	
}
