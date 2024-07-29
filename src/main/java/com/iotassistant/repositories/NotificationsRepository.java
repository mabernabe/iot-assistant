package com.iotassistant.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.notifications.DeviceOfflineNotification;
import com.iotassistant.models.notifications.Notification;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.notifications.SensorRuleNotification;

@Repository
@Scope("singleton")
public class NotificationsRepository {
	
	private @Autowired
	NotificationsJPARepository notificationsJPARepository;
	
	private @Autowired
	SensorRuleNotificationsJPARepository sensorRuleNotificationsJPARepository;
	
	private @Autowired
	TransductorOfflineNotificationsJPARepository transductorOfflineNotificationsJPARepository;
	
	private @Autowired
	CameraSensorRuleNotificationJPARepository cameraSensorRuleNotificationJPARepository;


	public Notification findNotificationById(int id) {
		return notificationsJPARepository.findOne(id); 
	}

	public void deleteNotificationById(Integer id) {
		notificationsJPARepository.delete(id);;
		
	}


	public void deleteAllNotifications() {
		notificationsJPARepository.deleteAll();	
	}
	
	public List<Notification> findAllNotifications() {
		return notificationsJPARepository.findAll();	
	}

	public Notification saveNotification(Notification notification) {
		return notificationsJPARepository.save(notification);
	}

	public List<SensorRuleNotification> findAllSensorRulesNotificationsByOrderByIdDesc() {
		return sensorRuleNotificationsJPARepository.findAllByOrderByIdDesc();
	}

	public List<DeviceOfflineNotification> findOfflineNotificationByOrderByIdDesc() {
		return transductorOfflineNotificationsJPARepository.findAllByOrderByIdDesc();
	}

	public SensorRuleCameraNotification findCameraSensorRuleNotificationById(Integer id) {
		return cameraSensorRuleNotificationJPARepository.findOne(id);
	}

}
