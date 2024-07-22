package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.controllers.dtos.devices.transductors.TransductorCapabilitiesDTO;

public class DevicesCapabilitiesDTO {
	
	private TransductorCapabilitiesDTO sensorCapabilities;
	
	private TransductorCapabilitiesDTO actuatorCapabilities;
	
	private CameraCapabilitiesDTO cameraCapabilities;

	public DevicesCapabilitiesDTO(TransductorCapabilitiesDTO sensorCapabilities,
			TransductorCapabilitiesDTO actuatorCapabilities, CameraCapabilitiesDTO cameraCapabilities) {
		super();
		this.sensorCapabilities = sensorCapabilities;
		this.actuatorCapabilities = actuatorCapabilities;
		this.cameraCapabilities =  cameraCapabilities;
	}

	public TransductorCapabilitiesDTO getSensorCapabilities() {
		return sensorCapabilities;
	}

	public TransductorCapabilitiesDTO getActuatorCapabilities() {
		return actuatorCapabilities;
	}

	public CameraCapabilitiesDTO getCameraCapabilities() {
		return cameraCapabilities;
	}
	
	

}
