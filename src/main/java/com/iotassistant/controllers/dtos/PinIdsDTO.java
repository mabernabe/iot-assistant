package com.iotassistant.controllers.dtos;

import java.util.List;


public class PinIdsDTO {
	List<String> pinIds;

	public PinIdsDTO(List<String> pinIds) {
		super();
		this.pinIds = pinIds;
	}

	public List<String> getPinIds() {
		return pinIds;
	}
	
	
}
