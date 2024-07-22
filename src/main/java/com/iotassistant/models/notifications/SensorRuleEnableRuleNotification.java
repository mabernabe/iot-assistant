package com.iotassistant.models.notifications;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.sensorrules.EnableRuleSensorRule;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
public class SensorRuleEnableRuleNotification extends SensorRuleNotification{
	
	private boolean sensorRuleNewState;
	
	@NotNull
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE) 
	private EnableRuleSensorRule enableSensorRule;

	
	public SensorRuleEnableRuleNotification() {
		super();
	}


	public SensorRuleEnableRuleNotification(EnableRuleSensorRule enableSensorRule, String sensorValue, String date) {
		super(sensorValue, date );
		this.sensorRuleNewState = enableSensorRule.isEnableAction();
		this.enableSensorRule = enableSensorRule;
	}


	public EnableRuleSensorRule getEnableSensorRule() {
		return enableSensorRule;
	}


	public boolean isSensorRuleNewState() {
		return sensorRuleNewState;
	}


	public void setSensorRuleNewState(boolean sensorRuleNewState) {
		this.sensorRuleNewState = sensorRuleNewState;
	}


	@Override
	public void accept(NotificationVisitor notificationVisitor) {
		notificationVisitor.visit(this);
		
	}


	@Override
	public Integer getSensorRuleId() {
		return enableSensorRule.getId();
	}


	@Override
	public String getSensorName() {
		return enableSensorRule.getSensorName();
	}


	@Override
	public PropertyMeasuredEnum getPropertyObserved() {
		return enableSensorRule.getPropertyObserved();
	}


	@Override
	public AnalogThresholdOperatorEnum getValueThresholdOperator() {
		return enableSensorRule.getAnalogThresholdOperator();
	}


	@Override
	public String getValueThresholdObserved() {
		return enableSensorRule.getValueThresholdObserved();
	}


	@Override
	public SensorRuleTriggerIntervalEnum getTimeBetweenTriggers() {
		return enableSensorRule.getTimeBetweenTriggers();
	}

}
