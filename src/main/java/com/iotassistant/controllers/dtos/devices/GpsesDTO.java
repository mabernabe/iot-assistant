package com.iotassistant.controllers.dtos.devices;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.devices.Gps;

public class GpsesDTO {
	
	private List<GpsDTO> gpses;

	public GpsesDTO(List<Gps> allGpses) {
		this.gpses = new ArrayList<GpsDTO>();
		for (Gps gps: allGpses) {
			this.gpses.add(new GpsDTO(gps));
		}
	}

	public List<GpsDTO> getGpses() {
		return gpses;
	}

}
