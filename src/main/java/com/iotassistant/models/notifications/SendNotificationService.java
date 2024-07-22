package com.iotassistant.models.notifications;

import org.springframework.beans.factory.annotation.Autowired;

import com.iotassistant.repositories.NotificationsRepository;


public abstract class SendNotificationService implements NotificationVisitor{
	
	@Autowired
	private NotificationsRepository notificationsRepository;
	
	
	public void send(Notification notification) {
		notificationsRepository.saveNotification(notification);
		notification.accept(this);
	}


}
