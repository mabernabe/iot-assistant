package com.iotassistant.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.notifications.Notification;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.notifications.SensorRuleNotification;
import com.iotassistant.models.notifications.DeviceOfflineNotification;

@Repository
@Scope("singleton")
public class NotificationsRepository {
	
	@Autowired
	NotificationsJPARepository notificationsJPARepository;
	
	@Autowired
	SensorRuleNotificationsJPARepository sensorRuleNotificationsJPARepository;
	
	@Autowired
	TransductorOfflineNotificationsJPARepository transductorOfflineNotificationsJPARepository;
	
	@Autowired
	CameraSensorRuleNotificationJPARepository cameraSensorRuleNotificationJPARepository;


	public Optional<Notification> findNotificationById(int id) {
		return notificationsJPARepository.findById(id); 
	}

	public void deleteNotificationById(Integer id) {
		notificationsJPARepository.deleteById(id);;
		
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

	public Optional<SensorRuleCameraNotification> findCameraSensorRuleNotificationById(Integer id) {
		return cameraSensorRuleNotificationJPARepository.findById(id);
	}

}
