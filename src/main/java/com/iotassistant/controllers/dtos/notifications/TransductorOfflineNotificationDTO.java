package com.iotassistant.controllers.dtos.notifications;

import com.iotassistant.models.notifications.DeviceOfflineNotification;

class TransductorOfflineNotificationDTO extends NotificationDTO{
	
	private String transductorName;

	TransductorOfflineNotificationDTO(DeviceOfflineNotification offlineNotification) {
		super(offlineNotification.getId(), offlineNotification.getDate());
		this.transductorName =  offlineNotification.getDeviceName();
	}

	public String getTransductorName() {
		return transductorName;
	}


	

}
