package com.iotassistant.controllers.dtos;

import java.util.List;

abstract class DeviceCapabilitiesDTO {
	
	private List<String> supportedInterfaces;
	
	private List<String> supportedWatchdogIntervals;
	
	
	DeviceCapabilitiesDTO(List<String> supportedInterfaces, List<String> supportedWatchdogIntervals) {
		this.supportedInterfaces = supportedInterfaces;
		this.supportedWatchdogIntervals = supportedWatchdogIntervals;
	}

	public List<String> getSupportedInterfaces() {
		return supportedInterfaces;
	}


	public List<String> getSupportedWatchdogIntervals() {
		return supportedWatchdogIntervals;
	}

}
