package com.iotassistant.controllers.dtos;

import com.iotassistant.models.ServerStatus;

public class ServerStatusDTO{
	
	private String interfaceName;
	
	private boolean isConnected;
	
	private String detail;


	public ServerStatusDTO(ServerStatus serverStatus) {
		this.interfaceName = serverStatus.getInterfaceName();
		this.isConnected = serverStatus.isConnected();
		this.detail = serverStatus.getDetail();
	}

	public String getDetail() {
		return detail;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}

	public boolean isConnected() {
		return isConnected;
	}

	
}
