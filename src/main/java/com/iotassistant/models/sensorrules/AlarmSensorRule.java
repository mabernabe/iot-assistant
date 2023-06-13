package com.iotassistant.models.sensorrules;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.notifications.SensorRuleAlarmNotification;
import com.iotassistant.models.transductor.SensorMeasureValueEvent;

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
	protected void triggerRule(SensorMeasureValueEvent sensorEvent) {
		SensorRuleAlarmNotification SensorRuleAlarmNotification = new SensorRuleAlarmNotification(this, sensorEvent.getValue(), sensorEvent.getDate());
		notificationHandler.handle( SensorRuleAlarmNotification);		
	}

	@Override
	public void accept(SensorRuleVisitor sensorRuleVisitor) {
		sensorRuleVisitor.visit(this);		
	}
	


}
