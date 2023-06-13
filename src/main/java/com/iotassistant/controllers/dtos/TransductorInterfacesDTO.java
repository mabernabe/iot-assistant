package com.iotassistant.controllers.dtos;

import java.util.List;

public class TransductorInterfacesDTO {
	
	List<String> interfaces;

	public TransductorInterfacesDTO(List<String> interfaces) {
		super();
		this.interfaces = interfaces;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}
	
	

}
