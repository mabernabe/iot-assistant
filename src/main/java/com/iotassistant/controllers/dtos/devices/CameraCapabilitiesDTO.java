package com.iotassistant.controllers.dtos.devices;

import java.util.List;

public class CameraCapabilitiesDTO extends DeviceCapabilitiesDTO{

	public CameraCapabilitiesDTO(List<String> supportedInterfaces, List<String> supportedWatchdogIntervals) {
		super(supportedInterfaces, supportedWatchdogIntervals);
	}

}
