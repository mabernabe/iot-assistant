package com.iotassistant.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.Device;
import com.iotassistant.models.notifications.DeviceOfflineNotification;
import com.iotassistant.models.notifications.Notification;
import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.notifications.TelegramNotificationsHandler;
import com.iotassistant.repositories.NotificationsRepository;

@Service
@Transactional
public class NotificationsService {
	
	private static NotificationsService instance;

	@Autowired
	private NotificationsRepository notificationsRepository;
	
	@Autowired
	private TelegramNotificationsHandler telegramNotificationHandler;
	
	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	
	public static NotificationsService getInstance() {
		return instance;
	}
	
	public void deleteNotificationById(int id) {
		notificationsRepository.deleteNotificationById(Integer.valueOf(id));
		
	}


	NotificationHandler getNotificationHandler(NotificationTypeEnum notificationType) {
		assert(notificationType == NotificationTypeEnum.TELEGRAM);
		return telegramNotificationHandler;		
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
		return NotificationTypeEnum.getAvailableNotificationTypes();
	}

	public NotificationHandler getAvailableNotificationHandler() {
			return telegramNotificationHandler;	
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


	public DeviceOfflineNotification getLastOfflineNotification(Device device) {
		List<DeviceOfflineNotification> offlineNotifications = notificationsRepository.findOfflineNotificationByOrderByIdDesc();
		for (DeviceOfflineNotification offlineNotification : offlineNotifications) {
			if (offlineNotification.getDeviceName().equals(device.getName())) {
				return offlineNotification;
			}
		}
		return null;	
	}

}
