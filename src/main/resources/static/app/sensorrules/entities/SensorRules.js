class SensorRules {
	
	constructor(alarmSensorRules, enableSensorRules, triggerActuatorSensorRules, cameraSensorRules) {
		this.alarmSensorRules = alarmSensorRules;
		this.enableSensorRules = enableSensorRules;
		this.triggerActuatorSensorRules = triggerActuatorSensorRules;
		this.cameraSensorRules = cameraSensorRules;
	}
	
	getAlarmSensorRules() {
		return this.alarmSensorRules;
	}
	
	getEnableSensorRules() {
		return this.enableSensorRules;
	}
	
	getTriggerActuatorSensorRules() {
		return this.triggerActuatorSensorRules;
	}
	
	getCameraSensorRules() {
		return this.cameraSensorRules;
	}
	
	getAllSensorRules() {
		var allSensorRules = this.alarmSensorRules;
		allSensorRules.concat(this.enableSensorRules);
		allSensorRules.concat(this.triggerActuatorSensorRules);
		allSensorRules.concat(this.cameraSensorRules);
		return allSensorRules;
	}
	
}