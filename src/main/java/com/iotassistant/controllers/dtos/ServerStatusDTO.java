package com.iotassistant.controllers.dtos;

public class ServerStatusDTO{
	
	private String interfaceName;
	
	private boolean isConnected;
	
	private String detail;

	public ServerStatusDTO(String interfaceName, boolean isAvailable, String broker) {
		this.interfaceName = interfaceName;
		this.isConnected = isAvailable;
		this.detail = broker;
	}

	public String getBroker() {
		return detail;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

	public boolean isAvailable() {
		return isConnected;
	}

	
}
