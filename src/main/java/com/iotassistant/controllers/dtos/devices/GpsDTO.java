package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.models.devices.Gps;

public class GpsDTO extends DeviceDTO{
	
	private GpsPositionDTO position;

	public GpsDTO(Gps gps) {
		super(gps);
		this.position = gps.isActive() ? new GpsPositionDTO(gps.getPosition()) : null;
		
	}

	public GpsPositionDTO getPosition() {
		return position;
	}

}
