class EnableRuleSensorRule extends SensorRule{
	
	constructor(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, sensorRuleId, enableAction) {
		super(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType);
		this.sensorRuleId = sensorRuleId;
		this.enableAction = enableAction;
	}
	
	getSensorRuleId() {
		return this.sensorRuleId;
	}
	
	isEnableAction() {
		return this.enableAction;
	}

	setEnableAction(enableAction) {
		this.enableAction = enableAction;
	}

	
	isValid() {
		return super.isValid() && this.sensorRuleId != null && this.enableAction != null;
	}
	
	accept(sensorRuleVisitor) {
		sensorRuleVisitor.visitEnableSensorRule(this);
	}
	
	static get enableRuleSensorRuleType() {
    	return 'Switch sensor rule';
  	}
	
}