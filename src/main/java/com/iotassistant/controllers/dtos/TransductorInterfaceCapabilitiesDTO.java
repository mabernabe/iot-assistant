package com.iotassistant.controllers.dtos;

public class TransductorInterfaceCapabilitiesDTO {
	
	private String interfaceName;
	
	private boolean isAvailable;
	

	public TransductorInterfaceCapabilitiesDTO(String interfaceName, boolean isAvailable) {
		super();
		this.interfaceName = interfaceName;
		this.isAvailable = isAvailable;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public boolean isAvailable() {
		return isAvailable;
	}


	

}
