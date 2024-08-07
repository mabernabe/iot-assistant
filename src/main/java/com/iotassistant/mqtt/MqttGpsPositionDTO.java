package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.devices.GpsPosition;
import com.iotassistant.utils.Date;

//GPS JSON Example : { "longitude" : "40.753", "latitude": "-73.983", "date" : "2022-03-23 15:41:03"} 
public class MqttGpsPositionDTO {
	
	private List<String> errors;
	
	private String longitude;
	
	private String latitude;

	private String date;
	
	@JsonCreator MqttGpsPositionDTO(@JsonProperty(value = "longitude" , required = true) String longitude,
									@JsonProperty(value = "latitude" , required = true) String latitude,
									@JsonProperty(value = "date", required = true) String date){
		super();
		this.errors = new ArrayList<String>();
		this.deserialize(longitude, latitude, date);
	}
	
	private void deserialize(String longitude, String latitude, String date)  {
		if (!Date.isValidDate(date)) {
			this.getErrors().add("Invalid date");
		}
		if (!GpsPosition.isInvalidPosition(longitude, latitude)) {
			this.getErrors().add("position is invalid, longitude: " + longitude + " latitude: " + latitude);	
		}
		this.longitude = longitude;
		this.latitude = latitude;
		this.date = date;
			
	}


	List<String> getErrors() {
		return errors;
	}
	
	boolean hasErrors() {
		return !this.getErrors().isEmpty();
	}

	protected GpsPosition getPosition() {
		return new GpsPosition(this.longitude, this.latitude, date);
	}

}
