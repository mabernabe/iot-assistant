package com.iotassistant.controllers.dtos.devices;

import java.util.List;

public class GpsCapabilitiesDTO extends DeviceCapabilitiesDTO{

	public GpsCapabilitiesDTO(List<String> supportedInterfaces, List<String> supportedWatchdogIntervals) {
		super(supportedInterfaces, supportedWatchdogIntervals);
	}
}
