package com.iotassistant.models.notifications;

import java.text.ParseException;
import java.util.List;

import com.iotassistant.repositories.NotificationsRepository;
import com.iotassistant.utils.Date;


public abstract class NotificationHandler{
	
	protected NotificationsRepository notificationsRepository;
	
	private static final int MIN_INTERVALM_BETWEEN_DEVICE_OFFLINE_NOTIFICATIONS = 60;

    public NotificationHandler(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    
	public boolean handle(SensorRuleNotification sensorRuleNotification) {
		SensorRuleNotification lastNotification = getLastSensorRuleNotification(sensorRuleNotification.getSensorRuleId());
		String newNotificationDate = sensorRuleNotification.getDate();
		boolean isTimeBetweenTriggersReached = isTimeIntervalReached(lastNotification, newNotificationDate, sensorRuleNotification.getTimeBetweenTriggers().getMinutes()) ;
		if (isTimeBetweenTriggersReached) {
			sensorRuleNotification = (SensorRuleNotification) notificationsRepository.saveNotification(sensorRuleNotification);
			this.sendNotification(sensorRuleNotification);
		}
		return isTimeBetweenTriggersReached;
	}
	
	private SensorRuleNotification getLastSensorRuleNotification(Integer sensorRuleId) {
		List<SensorRuleNotification> sensorRuleNotifications = notificationsRepository.findAllSensorRulesNotificationsByOrderByIdDesc();
		for(SensorRuleNotification sensorRuleNotification: sensorRuleNotifications) {
			if (sensorRuleNotification.getSensorRuleId().equals(sensorRuleId)) {
				return sensorRuleNotification;
			}
		}
		return null;
	}

	
	protected abstract void sendNotification(Notification notification);

	private boolean isTimeIntervalReached(Notification lastNotification, String newNotificationDate, int notificationMinIntervalMinutes) {
		boolean notificationIntervalReached = false;
		if (lastNotification == null) { //This is first notification
			notificationIntervalReached = true;
		}
		else {
			try {
				notificationIntervalReached = Date.havePassedMinutesBetweenDates(lastNotification.getDate(), newNotificationDate, notificationMinIntervalMinutes);
			} catch (ParseException e) {
			}
		}
		return notificationIntervalReached;		
	}

	
	public void handle(DeviceOfflineNotification offlineNotification) {		
		String newNotificationDate = offlineNotification.getDate();
		Notification notification = getLastOfflineNotification(offlineNotification.getDeviceName());
		if (isTimeIntervalReached(notification, newNotificationDate, MIN_INTERVALM_BETWEEN_DEVICE_OFFLINE_NOTIFICATIONS) ) {
			notificationsRepository.saveNotification(offlineNotification);
			this.sendNotification(offlineNotification);
		}
	}


	private DeviceOfflineNotification getLastOfflineNotification(String transductorName) {
		List<DeviceOfflineNotification> transductorOfflineNotifications = notificationsRepository.findOfflineNotificationByOrderByIdDesc();
		for(DeviceOfflineNotification transductorOfflineNotification: transductorOfflineNotifications) {
			if (transductorOfflineNotification.getDeviceName().equals(transductorName)) {
				return transductorOfflineNotification;
			}
		}
		return null;
	}


	
	
	

}
