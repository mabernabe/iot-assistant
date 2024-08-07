package com.iotassistant.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.iotassistant.models.notifications.SendNotificationService;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.services.ActuatorsService;

@Entity
@DiscriminatorValue("triggerActuatorSensorRule")
public class TriggerActuatorSensorRule extends SensorRule{
	
	private String actuatorName;
	
	@Enumerated(EnumType.STRING)
	private PropertyActuatedEnum propertyActuated;
	
	private String actuatorSetValue;
	
	@Transient
	private ActuatorsService actuatorsService;
	
	@Transient
	private SendNotificationService notificationHandler;
	
	

	public TriggerActuatorSensorRule() {
		super();
	}

	public TriggerActuatorSensorRule(SensorMeasureThresholdSettings sensorMeasureThresholdSettings, boolean enabled, SensorRuleTriggerIntervalEnum timeBetweenTriggers,
			String actuatorName, String actuatorSetValue, PropertyActuatedEnum propertyActuated, NotificationTypeEnum notificationType) {
		super(sensorMeasureThresholdSettings, enabled, SensorRuleType.TRIGGER_ACTUATOR, timeBetweenTriggers, notificationType);
		this.actuatorName = actuatorName;
		this.actuatorSetValue = actuatorSetValue;
		this.propertyActuated = propertyActuated;
	}

	public String getActuatorName() {
		return actuatorName;
	}


	public PropertyActuatedEnum getPropertyActuated() {
		return propertyActuated;
	}


	public String getActuatorSetValue() {
		return actuatorSetValue;
	}
	
	@Override
	public void accept(SensorRuleVisitor sensorRuleVisitor) {
		sensorRuleVisitor.visit(this);
		
	}


	public void setActuatorsService(ActuatorsService actuatorsService) {
		this.actuatorsService = actuatorsService;
		
	}


	public void setNotificationHandler(SendNotificationService notificationHandler) {
		this.notificationHandler = notificationHandler;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TriggerActuatorSensorRule other = (TriggerActuatorSensorRule) obj;
		if (actuatorName != other.getActuatorName())
			return false;
		if (!super.equals(obj))
			return false;	
		return true;
	}

}
