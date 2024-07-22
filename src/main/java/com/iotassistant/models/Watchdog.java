package com.iotassistant.models;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iotassistant.models.notifications.DeviceOfflineNotification;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.services.DevicesService;
import com.iotassistant.services.NotificationsService;
import com.iotassistant.utils.Date;

@Component
public class Watchdog implements DeviceVisitor {
	
	private static int TIME_MINUTES_BETWEEN_OFFLINE_NOTIFICATIONS = 60; 
	
	private static NotificationTypeEnum DEVICE_OFFLINE_NOTIFICATION_TYPE = NotificationTypeEnum.TELEGRAM;

	@Autowired
	DevicesService devicesService;

	@Autowired
	NotificationsService notificationService;


	@Scheduled(fixedRate = WatchdogInterval.WATCHDOG_MINIMUM_INTERVAL_SEC)
	public void watchdog()  {
		List<Device> allDevices = devicesService.getAllDevices();
		for (Device device : allDevices) {
			if (device.isWatchdogEnabled()) {
				device.accept(this);
			}			
		}	
	}
	
	@Override
	public void visit(Transductor transductor) {
		String lastValueDate = transductor.getLastValueDate();
		if (lastValueDate != null && 
			timeSincelastValueReachedWatchdogInterval(lastValueDate, transductor.getWatchdogInterval()) &&
			timeSinceLastNotificationReachMinInterval(transductor) ) {
				sendNotification(transductor);
		}	
	}


	private boolean timeSinceLastNotificationReachMinInterval(Device device) {
		DeviceOfflineNotification deviceOfflineNotification = notificationService.getLastOfflineNotification(device);
		if (deviceOfflineNotification == null) {
			return true;
		}
		try {
			return Date.havePassedMinutesBetweenDates(deviceOfflineNotification.getDate(), Date.getCurrentDate(), TIME_MINUTES_BETWEEN_OFFLINE_NOTIFICATIONS);
		} catch (ParseException e) {
			return true;
		}
	}


	private void sendNotification(Device device) {
		DeviceOfflineNotification offlineNotification = new DeviceOfflineNotification(device, Date.getCurrentDate());
		notificationService.sendNotification(DEVICE_OFFLINE_NOTIFICATION_TYPE, offlineNotification);

		
	}

	@Override
	public void visit(Camera camera) {
		try {
			devicesService.getCameraPicture(camera);
		} catch (CameraInterfaceException e) {
			if (timeSinceLastNotificationReachMinInterval(camera)) {
				sendNotification(camera);
			}
		}
	}
	


	private boolean timeSincelastValueReachedWatchdogInterval(String date, WatchdogInterval watchdogInterval) {	
		try {
			return watchdogInterval.isWatchdogIntervalReached(date);
		} catch (ParseException e) {
			return false;
		}

	}

}
