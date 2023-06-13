class AlarmSensorRule extends SensorRule{
		
	
	constructor(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType) {
		super(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType);
	}
	
	isValid() {
		return super.isValid();
	}
	
	accept(sensorRuleVisitor) {
		sensorRuleVisitor.visitAlarmSensorRule(this);
	}
	
	static get alarmSensorRuleType() {
    	return 'Sensor alarm';
  	}

}