package com.iotassistant.models;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iotassistant.models.notifications.DeviceOfflineNotification;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.services.DevicesService;
import com.iotassistant.services.NotificationsService;
import com.iotassistant.utils.Date;

@Component
public class Watchdog implements DeviceVisitor {

	@Autowired
	DevicesService devicesService;

	@Autowired
	NotificationsService notificationService;

	private ConcurrentHashMap<Device, String> devicesOffline = new ConcurrentHashMap<Device, String>(); 

	@Scheduled(fixedRate = 60000)
	public void watchdog()  {
		List<Device> allDevices = devicesService.getAllDevices();
		for (Device deviceOffline : devicesOffline.keySet()) {
			if (!allDevices.contains(deviceOffline)) { //Device no longer exist
				devicesOffline.remove(deviceOffline);
			}
		}
		for (Device device : allDevices) {
			if (device.isWatchdogEnabled()) {
				device.accept(this);
			}			
		}	
	}
	
	@Override
	public void visit(Transductor transductor) {
		if (transductor.isActive() && lastValueDateReachedWatchdogInterval(transductor.getLastValueDate(), transductor.getWatchdogInterval())) {
				sendNotification(transductor);
		}	
	}


	private boolean OfflineDateReachedWatchdotInterval(Device device) {
		if (devicesOffline.containsKey(device)) {
			String deviceOfflineDate = devicesOffline.get(device);
			return lastValueDateReachedWatchdogInterval(deviceOfflineDate,  device.getWatchdogInterval());
		}
		return false;
		
	}

	private void sendNotification(Device device) {
		DeviceOfflineNotification offlineNotification = new DeviceOfflineNotification(device, Date.getCurrentDate());
		notificationService.getAvailableNotificationHandler().handle(offlineNotification);

		
	}

	@Override
	public void visit(Camera camera) {
		boolean offlineReachedWatchdogInterval = false;
		try {
			camera.getPicture();
		} catch (CameraInterfaceException e) {
			offlineReachedWatchdogInterval = OfflineDateReachedWatchdotInterval(camera);
			if (!devicesOffline.containsKey(camera)) {
				devicesOffline.put(camera, Date.getCurrentDate());			
			}
		}
		if (offlineReachedWatchdogInterval) {
			sendNotification(camera);
		}
	}
	


	private boolean lastValueDateReachedWatchdogInterval(String date, WatchdogInterval watchdogInterval) {	
		boolean isWatchdogIntervalReached = false;
		try {
			if (watchdogInterval.isWatchdogIntervalReached(date)) {
				isWatchdogIntervalReached = true;
			}
		} catch (ParseException e) {
		}
		return isWatchdogIntervalReached;
	}





}
