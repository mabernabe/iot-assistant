package com.iotassistant.models.notifications;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class  SensorRuleNotification extends Notification implements SensorMeasureNotification{

	
	@NotNull
	private String sensorValue;
	
	SensorRuleNotification(String sensorValue, String date) {
		super(date);
		this.sensorValue = sensorValue;
	}
	
	public SensorRuleNotification() {
		super();
	}

	public abstract Integer getSensorRuleId() ;
	
	
	public String getSensorValue() {
		return sensorValue;
	}


	public void setSensorValue(String sensorValue) {
		this.sensorValue = sensorValue;
	}
	

	@Override
	public abstract String getSensorName() ;


	@Override
	public abstract PropertyMeasuredEnum getPropertyObserved();

	@Override
	public abstract AnalogThresholdOperatorEnum getValueThresholdOperator();


	@Override
	public abstract String getValueThresholdObserved();

	public abstract SensorRuleTriggerIntervalEnum getTimeBetweenTriggers() ;
}
