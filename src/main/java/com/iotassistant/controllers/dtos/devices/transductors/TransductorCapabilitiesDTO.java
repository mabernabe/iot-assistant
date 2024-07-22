package com.iotassistant.controllers.dtos.devices.transductors;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.controllers.dtos.devices.DeviceCapabilitiesDTO;
import com.iotassistant.models.devices.Property;

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
