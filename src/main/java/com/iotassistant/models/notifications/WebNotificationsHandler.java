package com.iotassistant.models.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.repositories.NotificationsRepository;

@Component
public class WebNotificationsHandler extends NotificationHandler{

	@Autowired
    public WebNotificationsHandler(NotificationsRepository notificationsRepository) {
        super(notificationsRepository);
    }


	@Override
	protected void sendNotification(Notification notification) {		
	}






}
