package com.iotassistant.controllers.dtos.notifications;

import com.iotassistant.controllers.NotificationsController;
import com.iotassistant.controllers.dtos.CameraSensorRuleDTO;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;

public class SensorRuleCameraNotificationDTO extends SensorRuleNotificationDTO{
	
	private CameraSensorRuleDTO cameraSensorRule;
	
	private String pictureURL;

	public SensorRuleCameraNotificationDTO(SensorRuleCameraNotification sensorRuleCameraNotification, String baseUrlNotificationsAttachment) {
		super(sensorRuleCameraNotification);
		this.cameraSensorRule = new CameraSensorRuleDTO(sensorRuleCameraNotification.getCameraSensorRule());
		this.pictureURL = baseUrlNotificationsAttachment + sensorRuleCameraNotification.getId() + NotificationsController.CAMERA_SENSOR_RULE_NOTIFICATION_ATTACHMENT;
	}

	public CameraSensorRuleDTO getCameraSensorRule() {
		return cameraSensorRule;
	}

	public String getPictureURL() {
		return pictureURL;
	}
	
	

}
