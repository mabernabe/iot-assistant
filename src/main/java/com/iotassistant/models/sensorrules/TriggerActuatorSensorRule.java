package com.iotassistant.models.sensorrules;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.notifications.SensorRuleTriggerActuatorNotification;
import com.iotassistant.models.transductor.SensorMeasureValueEvent;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.services.ActuatorsService;

@Entity
@DiscriminatorValue("triggerActuatorSensorRule")
public class TriggerActuatorSensorRule extends SensorRule{
	
	private String actuatorName;
	
	@Enumerated(EnumType.STRING)
	private PropertyActuatedEnum propertyActuated;
	
	private String actuatorSetValue;
	
	@Transient
	ActuatorsService actuatorsService;
	
	@Transient
	private NotificationHandler notificationHandler;
	
	

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

	@Override
	protected void triggerRule(SensorMeasureValueEvent sensorEvent) {
		SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification = new SensorRuleTriggerActuatorNotification(this, sensorEvent.getValue(), sensorEvent.getDate());
		boolean triggerIntervalReached = notificationHandler.handle(sensorRuleTriggerActuatorNotification);
		if (triggerIntervalReached) {			
			try {
				actuatorsService.setActuatorValue(propertyActuated, actuatorName, actuatorSetValue);
			} catch (TransductorInterfaceException e) {
				e.printStackTrace();
			}
		}
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


	public void setNotificationHandler(NotificationHandler notificationHandler) {
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
