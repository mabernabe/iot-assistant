package com.iotassistant.controllers.dtos;

public class SystemDTO  {
	
	private SystemCapabilitiesDTO capabilities;
	
	private String platformName;

	public SystemDTO(SystemCapabilitiesDTO capabilities, String platformName) {
		super();
		this.capabilities = capabilities;
		this.platformName = platformName;
	}

	public SystemCapabilitiesDTO getCapabilities() {
		return capabilities;
	}

	public String getPlatformName() {
		return platformName;
	}

	

}
