package com.iotassistant.models.notifications;

public abstract class SendNotificationService implements NotificationVisitor{	
	
	public void send(Notification notification) {
		notification.accept(this);
	}


}
