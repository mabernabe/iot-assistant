package com.iotassistant.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.controllers.dtos.devices.transductors.PropertyDTO;
import com.iotassistant.models.SensorMeasureThresholdSettings;
import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.TriggerActuatorSensorRule;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.notifications.NotificationTypeEnum;

public class TriggerActuatorSensorRuleDTO extends SensorRuleDTO{

	private String actuatorName;
	
	private PropertyDTO actuatorProperty;
	
	private String actuatorSetValue;

	
	public TriggerActuatorSensorRuleDTO() {
		super();
	}

	public	TriggerActuatorSensorRuleDTO(TriggerActuatorSensorRule triggerActuatorSensorRule) {
		super(triggerActuatorSensorRule);
		this.actuatorName = triggerActuatorSensorRule.getActuatorName();
		this.actuatorProperty = new PropertyDTO(triggerActuatorSensorRule.getPropertyActuated());
		this.actuatorSetValue = triggerActuatorSensorRule.getActuatorSetValue();		
	}

	
	@JsonIgnore
	public TriggerActuatorSensorRule getSensorRule() {
		PropertyActuatedEnum propertyActuatedEnum = PropertyActuatedEnum.getInstance(actuatorProperty.getName());
		SensorRuleTriggerIntervalEnum timeBetweenTriggers = SensorRuleTriggerIntervalEnum.getInstance(this.getTimeBetweenTriggers());
		NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.getNotificationType());
		SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
		return new TriggerActuatorSensorRule(sensorMeasureThresholdSettings, this.isEnabled(), timeBetweenTriggers, actuatorName, actuatorSetValue, propertyActuatedEnum, notificationType);
	}

	public String getActuatorName() {
		return actuatorName;
	}

	public void setActuatorName(String actuatorName) {
		this.actuatorName = actuatorName;
	}

	public PropertyDTO getActuatorProperty() {
		return actuatorProperty;
	}

	public void setActuatorProperty(PropertyDTO actuatorProperty) {
		this.actuatorProperty = actuatorProperty;
	}

	public String getActuatorSetValue() {
		return actuatorSetValue;
	}

	public void setActuatorSetValue(String actuatorSetValue) {
		this.actuatorSetValue = actuatorSetValue;
	}

	
	
}
