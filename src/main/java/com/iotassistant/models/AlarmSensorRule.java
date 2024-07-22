package com.iotassistant.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.notifications.NotificationTypeEnum;

@Entity
@DiscriminatorValue("alarmSensorRule")
public class AlarmSensorRule extends SensorRule{


	public AlarmSensorRule() {
		super();
	}
	
	public AlarmSensorRule(SensorMeasureThresholdSettings sensorMeasureThresholdSettings, NotificationTypeEnum notificationType, 
			SensorRuleTriggerIntervalEnum timeBetweenTriggers,  boolean enabled) {
		super(sensorMeasureThresholdSettings, enabled, SensorRuleType.SENSOR_ALARM, timeBetweenTriggers, notificationType);
	}
	


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	

	@Override
	public void accept(SensorRuleVisitor sensorRuleVisitor) {
		sensorRuleVisitor.visit(this);		
	}
	


}
