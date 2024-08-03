package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.models.devices.GpsPosition;

public class GpsPositionDTO {
	
	private String longitude;
	
	private String latitude;

	private String date;

	public GpsPositionDTO(GpsPosition position) {
		this.longitude = position.getLongitude();
		this.latitude = position.getLatitude();
		this.date = position.getDate();
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getDate() {
		return date;
	}
	
	

}
