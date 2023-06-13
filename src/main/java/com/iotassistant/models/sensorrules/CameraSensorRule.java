package com.iotassistant.models.sensorrules;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.notifications.SensorRuleCameraNotification;
import com.iotassistant.models.transductor.SensorMeasureValueEvent;
import com.iotassistant.services.CamerasService;

@Entity
@DiscriminatorValue("cameraSensorRule")
public class CameraSensorRule extends SensorRule{
	
	private String cameraName;
	
	@Transient
	CamerasService camerasService;

	
	public CameraSensorRule() {
		super();
	}

	public CameraSensorRule(SensorMeasureThresholdSettings sensorMeasureThresholdSettings, String cameraName, NotificationTypeEnum notificationType, 
			SensorRuleTriggerIntervalEnum timeBetweenTriggers,  boolean enabled) {
		super(sensorMeasureThresholdSettings, enabled, SensorRuleType.CAMERA, timeBetweenTriggers, notificationType);
		this.cameraName = cameraName;
	}

	@Override
	protected void triggerRule(SensorMeasureValueEvent sensorEvent) {
		byte[] picture = null;
		try {
			picture = camerasService.getPicture(cameraName);		
		} catch (CameraInterfaceException e) {
		} finally	{
			SensorRuleCameraNotification SensorRuleAlarmNotification = new SensorRuleCameraNotification(this, picture, sensorEvent.getValue(), sensorEvent.getDate());
			notificationHandler.handle( SensorRuleAlarmNotification);
		}
		
		
		
	}

	@Override
	public void accept(SensorRuleVisitor sensorRuleVisitor) {
		sensorRuleVisitor.visit(this);
		
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCamerasService(CamerasService camerasService) {
		this.camerasService = camerasService;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		CameraSensorRule other = (CameraSensorRule) obj;
		if (cameraName != other.getCameraName())
			return false;
		if (!super.equals(obj))
			return false;	
		return true;
	}

	
}
