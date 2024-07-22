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
		let allSensorRules = this.alarmSensorRules;
		allSensorRules = allSensorRules.concat(this.enableSensorRules);
		allSensorRules = allSensorRules.concat(this.triggerActuatorSensorRules);
		allSensorRules = allSensorRules.concat(this.cameraSensorRules);
		return allSensorRules;
	}
	
}