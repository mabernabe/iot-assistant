package com.iotassistant.models.notifications;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
public class SensorRuleTriggerActuatorNotification extends  SensorRuleNotification{
	
	@NotNull
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE) 
	private TriggerActuatorSensorRule triggerActuatorSensorRule;
	
	public SensorRuleTriggerActuatorNotification() {
		super();
	}


	public SensorRuleTriggerActuatorNotification(TriggerActuatorSensorRule triggerActuatorSensorRule, String sensorValue, String date) {
		super(sensorValue, date);
		this.triggerActuatorSensorRule = triggerActuatorSensorRule;
	}


	public TriggerActuatorSensorRule getSensorRule() {
		return triggerActuatorSensorRule;
	}

	
	@Override
	public void accept(NotificationVisitor notificationVisitor) {
		notificationVisitor.visit(this);		
	}
	
	@Override
	public Integer getSensorRuleId() {
		return triggerActuatorSensorRule.getId();
	}


	@Override
	public String getSensorName() {
		return triggerActuatorSensorRule.getSensorName();
	}


	@Override
	public PropertyMeasuredEnum getPropertyObserved() {
		return triggerActuatorSensorRule.getPropertyObserved();
	}


	@Override
	public AnalogThresholdOperatorEnum getValueThresholdOperator() {
		return triggerActuatorSensorRule.getAnalogThresholdOperator();
	}


	@Override
	public String getValueThresholdObserved() {
		return triggerActuatorSensorRule.getValueThresholdObserved();
	}


	@Override
	public SensorRuleTriggerIntervalEnum getTimeBetweenTriggers() {
		return triggerActuatorSensorRule.getTimeBetweenTriggers();
	}
	
}