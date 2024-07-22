package com.iotassistant.controllers.dtos.notifications;

import java.util.List;

public class NotificationsCapabilitiesDTO {
	
	private List<String> supportedNotificationsTypes;

	public NotificationsCapabilitiesDTO(List<String> supportedNotificationsTypes) {
		super();
		this.supportedNotificationsTypes = supportedNotificationsTypes;
	}

	public List<String> getSupportedNotificationsTypes() {
		return supportedNotificationsTypes;
	}
	
	

}
