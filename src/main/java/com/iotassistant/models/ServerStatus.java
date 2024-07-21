package com.iotassistant.models;

public class ServerStatus {
	
	private String interfaceName;
	
	private boolean isConnected;
	
	private String detail;

	public ServerStatus(String interfaceName, boolean isConnected, String detail) {
		super();
		this.interfaceName = interfaceName;
		this.isConnected = isConnected;
		this.detail = detail;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public String getDetail() {
		return detail;
	}
	
	

}
