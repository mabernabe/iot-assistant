package com.iotassistant.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.TelegramBotManager;
import com.iotassistant.models.notifications.Notification;
import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.notifications.TelegramNotificationsHandler;
import com.iotassistant.models.notifications.WebNotificationsHandler;
import com.iotassistant.repositories.NotificationsRepository;

@Service
@Transactional
public class NotificationsService {
	
	@Autowired
	NotificationsRepository notificationsRepository;
	
	@Autowired
	WebNotificationsHandler webNotificationHandler;
	
	@Autowired
	TelegramNotificationsHandler telegramNotificationHandler;
	
	@Autowired
	TelegramBotManager telegramBotManager;
	

	public void deleteNotificationById(int id) {
		notificationsRepository.deleteNotificationById(Integer.valueOf(id));
		
	}


	public NotificationHandler getNotificationHandler(NotificationTypeEnum notificationType) {
		if (notificationType.equals(NotificationTypeEnum.TELEGRAM)) {
			return telegramNotificationHandler;
		}
		else {
			return webNotificationHandler;
		}		
	}

    public Notification getNotificationById(int id) {
        Optional<Notification> optionalNotification = notificationsRepository.findNotificationById(id);
        if (optionalNotification.isPresent()) {
            return optionalNotification.get();
        }
        else {
            return null;
        }
    }

	public List<String> getAvailableNotificationTypes() {
		return NotificationTypeEnum.getAvailableNotificationTypes(telegramBotManager.connected());
	}

	public NotificationHandler getAvailableNotificationHandler() {
		if (telegramBotManager.connected()) {
			return telegramNotificationHandler;
		}
		else {
			return webNotificationHandler;
		}		
	}


	

	public void deleteAllNotifications() {
		notificationsRepository.deleteAllNotifications();	
	}

	public List<Notification> getAllNotifications() {
		return notificationsRepository.findAllNotifications();
	}


	public byte[] getCameraSensorRuleAttachment(int id) {
		SensorRuleCameraNotification sensorRuleCameraNotification = notificationsRepository.findCameraSensorRuleNotificationById(id).get();
		return sensorRuleCameraNotification.getPicture();
	}

	

}
