package com.iotassistant.controllers.dtos.notifications;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.models.notifications.Notification;
import com.iotassistant.models.notifications.NotificationVisitor;
import com.iotassistant.models.notifications.SensorRuleAlarmNotification;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.notifications.SensorRuleEnableRuleNotification;
import com.iotassistant.models.notifications.SensorRuleTriggerActuatorNotification;
import com.iotassistant.models.notifications.DeviceOfflineNotification;

public class NotificationsDTO implements NotificationVisitor{
	
	private ArrayList<SensorAlarmNotificationDTO> sensorAlarmNotifications = new ArrayList<SensorAlarmNotificationDTO>();

	private ArrayList<TransductorOfflineNotificationDTO> offlineNotifications = new ArrayList<TransductorOfflineNotificationDTO>();
	
	private ArrayList<SensorRuleEnableRuleNotificationDTO> sensorRuleEnableRuleNotifications = new ArrayList<SensorRuleEnableRuleNotificationDTO>();

	private ArrayList<SensorRuleTriggerActuatorNotificationDTO> sensorRuleTriggerActuatorNotifications = new ArrayList<SensorRuleTriggerActuatorNotificationDTO>();

	private ArrayList<SensorRuleCameraNotificationDTO> sensorRuleCameraNotifications = new ArrayList<SensorRuleCameraNotificationDTO>();

	private String baseUrlNotificationsAttachment;
	
	public NotificationsDTO() {
		super();
	}

	public NotificationsDTO(List<Notification> allNotifications, String baseUrlNotificationsAttachment) {
		super();
		this.baseUrlNotificationsAttachment = baseUrlNotificationsAttachment;
		for (Notification notification: allNotifications) {
			notification.accept(this);
		}
	}

	public ArrayList<SensorAlarmNotificationDTO> getSensorAlarmNotifications() {
		return sensorAlarmNotifications;
	}

	public ArrayList<TransductorOfflineNotificationDTO> getOfflineNotifications() {
		return offlineNotifications;
	}

	public ArrayList<SensorRuleEnableRuleNotificationDTO> getSensorRuleEnableRuleNotifications() {
		return sensorRuleEnableRuleNotifications;
	}

	public ArrayList<SensorRuleTriggerActuatorNotificationDTO> getSensorRuleTriggerActuatorNotifications() {
		return sensorRuleTriggerActuatorNotifications;
	}
	

	public ArrayList<SensorRuleCameraNotificationDTO> getSensorRuleCameraNotifications() {
		return sensorRuleCameraNotifications;
	}

	@JsonIgnore
	@Override
	public void visit(SensorRuleAlarmNotification sensorRuleAlarmNotification) {
		this.sensorAlarmNotifications.add(new SensorAlarmNotificationDTO(sensorRuleAlarmNotification));	
	}

	@JsonIgnore
	@Override
	public void visit(SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification) {
		this.sensorRuleEnableRuleNotifications.add(new SensorRuleEnableRuleNotificationDTO(sensorRuleEnableRuleNotification));		
	}

	@JsonIgnore
	@Override
	public void visit(SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification) {
		this.sensorRuleTriggerActuatorNotifications.add(new SensorRuleTriggerActuatorNotificationDTO(sensorRuleTriggerActuatorNotification));				
	}

	@JsonIgnore
	@Override
	public void visit(DeviceOfflineNotification offlineNotification) {
		this.offlineNotifications.add(new TransductorOfflineNotificationDTO(offlineNotification));	
	}

	@Override
	public void visit(SensorRuleCameraNotification sensorRuleCameraNotification) {
		this.sensorRuleCameraNotifications.add(new SensorRuleCameraNotificationDTO(sensorRuleCameraNotification, baseUrlNotificationsAttachment));
		
	}
	
	
}
