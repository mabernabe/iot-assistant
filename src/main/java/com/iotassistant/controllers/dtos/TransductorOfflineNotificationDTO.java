package com.iotassistant.controllers.dtos;

import com.iotassistant.models.notifications.DeviceOfflineNotification;

public class TransductorOfflineNotificationDTO extends NotificationDTO{
	
	private String transductorName;

	public TransductorOfflineNotificationDTO(DeviceOfflineNotification offlineNotification) {
		super(offlineNotification.getId(), offlineNotification.getDate());
		this.transductorName =  offlineNotification.getDeviceName();
	}

	public String getTransductorName() {
		return transductorName;
	}


	

}
