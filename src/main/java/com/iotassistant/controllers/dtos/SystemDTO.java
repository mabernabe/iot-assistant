package com.iotassistant.controllers.dtos;

public class SystemDTO  {
	
	private SystemCapabilitiesDTO capabilities;
	
	private String platformName;
	
	private String uptime;
	
	public SystemDTO(SystemCapabilitiesDTO capabilities, String platformName, String uptime) {
		super();
		this.capabilities = capabilities;
		this.platformName = platformName;
		this.uptime = uptime;
	}

	public SystemCapabilitiesDTO getCapabilities() {
		return capabilities;
	}

	public String getPlatformName() {
		return platformName;
	}

	public String getUptime() {
		return uptime;
	}

	

}
