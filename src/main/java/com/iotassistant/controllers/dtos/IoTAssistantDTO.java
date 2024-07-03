package com.iotassistant.controllers.dtos;

public class IoTAssistantDTO  {
	
	private IotAssistantCapabilitiesDTO capabilities;
	
	private String platformName;

	public IoTAssistantDTO(IotAssistantCapabilitiesDTO capabilities, String platformName) {
		super();
		this.capabilities = capabilities;
		this.platformName = platformName;
	}

	public IotAssistantCapabilitiesDTO getCapabilities() {
		return capabilities;
	}

	public String getPlatformName() {
		return platformName;
	}

	

}
