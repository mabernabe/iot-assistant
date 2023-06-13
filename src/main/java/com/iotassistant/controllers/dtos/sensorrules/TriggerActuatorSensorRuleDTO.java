package com.iotassistant.controllers.dtos.sensorrules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotassistant.controllers.dtos.PropertyDTO;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.sensorrules.SensorMeasureThresholdSettings;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class TriggerActuatorSensorRuleDTO extends SensorRuleDTO{

	private String actuatorName;
	
	private PropertyDTO actuatorProperty;
	
	private String actuatorSetValue;

	
	public TriggerActuatorSensorRuleDTO() {
		super();
	}

	public TriggerActuatorSensorRuleDTO(TriggerActuatorSensorRule triggerActuatorSensorRule) {
		super(triggerActuatorSensorRule);
		this.actuatorName = triggerActuatorSensorRule.getActuatorName();
		this.actuatorProperty = new PropertyDTO(triggerActuatorSensorRule.getPropertyActuated());
		this.actuatorSetValue = triggerActuatorSensorRule.getActuatorSetValue();		
	}

	
	@JsonIgnore
	public TriggerActuatorSensorRule getSensorRule() {
		PropertyActuatedEnum propertyActuatedEnum = PropertyActuatedEnum.getInstance(actuatorProperty.getName());
		SensorRuleTriggerIntervalEnum timeBetweenTriggers = SensorRuleTriggerIntervalEnum.getInstance(this.getTimeBetweenTriggers());
		NotificationTypeEnum notificationType = NotificationTypeEnum.getInstance(this.notificationType);
		SensorMeasureThresholdSettings sensorMeasureThresholdSettings = this.sensorMeasureThresholdSettings.getSensorMeasureThresholdSettings();
		return new TriggerActuatorSensorRule(sensorMeasureThresholdSettings, enabled, timeBetweenTriggers, actuatorName, actuatorSetValue, propertyActuatedEnum, notificationType);
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
