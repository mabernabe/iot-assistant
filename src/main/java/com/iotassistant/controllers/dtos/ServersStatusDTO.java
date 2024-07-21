package com.iotassistant.controllers.dtos;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.ServerStatus;

public class ServersStatusDTO {
	
	private List<ServerStatusDTO> serversStatus;

	public ServersStatusDTO(List<ServerStatus> serversStatus) {
		super();
		this.serversStatus = new ArrayList<ServerStatusDTO>();
		for (ServerStatus serverStatus : serversStatus) {
			this.serversStatus.add(new ServerStatusDTO(serverStatus));
		}
	}

	public List<ServerStatusDTO> getServersStatus() {
		return serversStatus;
	}
	
	

}
