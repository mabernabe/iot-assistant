package com.iotassistant.controllers.dtos;

import java.util.List;

public class ServersStatusDTO {
	
	List<ServerStatusDTO> serversStatus;

	public ServersStatusDTO(List<ServerStatusDTO> serversInterfaceStatus) {
		super();
		this.serversStatus = serversInterfaceStatus;
	}

	public List<ServerStatusDTO> getServersInterfaceStatus() {
		return serversStatus;
	}
	
	

}
