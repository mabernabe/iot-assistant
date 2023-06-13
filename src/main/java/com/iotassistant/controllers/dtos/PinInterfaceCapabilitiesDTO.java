package com.iotassistant.controllers.dtos;

import java.util.List;

import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;

public class PinInterfaceCapabilitiesDTO extends TransductorInterfaceCapabilitiesDTO{
	
	List<String> availableDigitalPins;
	
	List<String> availableAnalogPins;
	
	String platformPinInterfaceName;

	public PinInterfaceCapabilitiesDTO(boolean isAvailable, List<String> availableAnalogPins, List<String> availableDigitalPins, String platformName) {
		super(TransductorInterfaceTypeEnum.PIN.toString(), isAvailable);
		this.availableAnalogPins = availableAnalogPins;
		this.availableDigitalPins = availableDigitalPins;
		this.platformPinInterfaceName = platformName;
	}


	public List<String> getAvailableDigitalPins() {
		return availableDigitalPins;
	}

	public List<String> getAvailableAnalogPins() {
		return availableAnalogPins;
	}

	public String getPlatformPinInterfaceName() {
		return platformPinInterfaceName;
	}
	
	

}
