package com.iotassistant.controllers.dtos.devices;

import java.util.List;

public abstract class DeviceCapabilitiesDTO {
	
	private List<String> supportedInterfaces;
	
	private List<String> supportedWatchdogIntervals;
	
	
	protected DeviceCapabilitiesDTO(List<String> supportedInterfaces, List<String> supportedWatchdogIntervals) {
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
