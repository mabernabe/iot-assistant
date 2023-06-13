package com.iotassistant.controllers.dtos;

public class NotificationDTO {
	
	private int id;
	
	private String date;

	public NotificationDTO(int id, String date) {
		super();
		this.id = id;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
