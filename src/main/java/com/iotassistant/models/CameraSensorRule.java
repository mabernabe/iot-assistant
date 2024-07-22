package com.iotassistant.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.services.CamerasService;

@Entity
@DiscriminatorValue("cameraSensorRule")
public class CameraSensorRule extends SensorRule{
	
	private String cameraName;
	
	private @Transient
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
