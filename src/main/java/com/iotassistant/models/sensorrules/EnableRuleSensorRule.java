package com.iotassistant.models.sensorrules;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.services.SensorRulesService;


@Entity
@DiscriminatorValue("switchAlarmSensorRule")
public class EnableRuleSensorRule extends SensorRule {
	
	private int sensorRuleId;
	
	private boolean enableAction;
	
	@Transient
	private SensorRulesService sensorRulesService;
	
	
	public EnableRuleSensorRule() {
		super();
	}

	public EnableRuleSensorRule(SensorMeasureThresholdSettings sensorMeasureThresholdSettings, boolean enabled, int sensorRuleId, 
			SensorRuleTriggerIntervalEnum timeBetweenTriggers, NotificationTypeEnum notificationType, boolean enableAction) {
		super(sensorMeasureThresholdSettings, enabled, SensorRuleType.ENABLE_DISABLE_SENSOR_RULE, timeBetweenTriggers, notificationType);
		this.sensorRuleId = sensorRuleId;
		this.enableAction = enableAction;
	}

	public int getSensorRuleId() {
		return sensorRuleId;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		EnableRuleSensorRule other = (EnableRuleSensorRule) obj;
		if (sensorRuleId != other.sensorRuleId)
			return false;
		if (!super.equals(obj))
			return false;	
		return true;
	}

	public void setSensorRulesService(SensorRulesService sensorRulesService) {
		this.sensorRulesService = sensorRulesService;
	}


	@Override
	public void accept(SensorRuleVisitor sensorRuleVisitor) {
		sensorRuleVisitor.visit(this);
		
	}

	

	public boolean isEnableAction() {
		return enableAction;
	}

	public SensorRulesService getSensorRulesService() {
		return sensorRulesService;
	}

}
