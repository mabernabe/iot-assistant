package com.iotassistant.controllers.dtos;

import java.util.List;

public class ServersStatusDTO {
	
	private List<ServerStatusDTO> serversStatus;

	public ServersStatusDTO(List<ServerStatusDTO> serversInterfaceStatus) {
		super();
		this.serversStatus = serversInterfaceStatus;
	}

	public List<ServerStatusDTO> getServersInterfaceStatus() {
		return serversStatus;
	}
	
	

}
