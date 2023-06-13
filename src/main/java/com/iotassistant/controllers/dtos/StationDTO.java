package com.iotassistant.controllers.dtos;

public class StationDTO  {
	
	private StationCapabilitiesDTO capabilities;
	
	private String platformName;

	public StationDTO(StationCapabilitiesDTO capabilities, String platformName) {
		super();
		this.capabilities = capabilities;
		this.platformName = platformName;
	}

	public StationCapabilitiesDTO getCapabilities() {
		return capabilities;
	}

	public String getPlatformName() {
		return platformName;
	}

	

}
