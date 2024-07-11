class SensorRulesMapper {
	
	constructor() {
		this.alarmSensorRuleMapper = new AlarmSensorRuleMapper();
		this.enableSensorRuleMapper = new EnableSensorRuleMapper();
		this.triggerActuatorSensorRuleMapper = new TriggerActuatorSensorRuleMapper();
		this.cameraSensorRuleMapper = new CameraSensorRuleMapper();
	}
	
	buildSensorRulesFromServiceObject(sensorRulesServiceObject) {
		var alarmSensorRules = this.#buildAlarmSensorRulesFromServiceObject(sensorRulesServiceObject.alarmSensorRules);
		var enableSensorRules = this.#buildEnableSensorRulesFromServiceObject(sensorRulesServiceObject.enableSensorRules)
		var triggerActuatorSensorRules = this.#buildTriggerActuatorSensorRulesFromServiceObject(sensorRulesServiceObject.triggerActuatorSensorRules);
		var cameraSensorRules = this.#buildCameraSensorRulesFromServiceObject(sensorRulesServiceObject.cameraSensorRules);
		return new SensorRules(alarmSensorRules, enableSensorRules, triggerActuatorSensorRules, cameraSensorRules);
	}	
	
	#buildAlarmSensorRulesFromServiceObject(alarmSensorRulesServiceObject) {
		var alarmSensorRules = [];
		alarmSensorRulesServiceObject.forEach(alarmSensorRuleServiceObject => {
			var alarmSensorRule = this.buildAlarmSensorRuleFromServiceObject(alarmSensorRuleServiceObject);
			alarmSensorRules.push(alarmSensorRule);
		})
		return alarmSensorRules;
	}
	
	buildAlarmSensorRuleFromServiceObject(alarmSensorRuleServiceObject) {
		return this.alarmSensorRuleMapper.buildAlarmSensorRuleFromServiceObject(alarmSensorRuleServiceObject);
	}
	
	#buildEnableSensorRulesFromServiceObject(enableSensorRulesServiceObject) {
		var enableSensorRules = [];
		enableSensorRulesServiceObject.forEach(enableSensorRuleServiceObject => {
			var enableSensorRule = this.buildEnableSensorRuleFromServiceObject(enableSensorRuleServiceObject);
			enableSensorRules.push(enableSensorRule);
		})
		return enableSensorRules;
	}
	
	buildEnableSensorRuleFromServiceObject(enableSensorRuleServiceObject){
		return this.enableSensorRuleMapper.buildEnableSensorRuleFromServiceObject(enableSensorRuleServiceObject);
	}
	
	
	#buildTriggerActuatorSensorRulesFromServiceObject(triggerActuatorSensorRulesServiceObject) {
		var triggerActuatorSensorRules = [];
		triggerActuatorSensorRulesServiceObject.forEach(triggerActuatorSensorRuleServiceObject => {
			var triggerActuatorSensorRule = this.buildTriggerActuatorSensorRuleFromServiceObject(triggerActuatorSensorRuleServiceObject);
			triggerActuatorSensorRules.push(triggerActuatorSensorRule);
		})
		return triggerActuatorSensorRules;
	}	
	
	buildTriggerActuatorSensorRuleFromServiceObject(triggerActuatorSensorRuleServiceObject) {
		 return this.triggerActuatorSensorRuleMapper.buildTriggerActuatorSensorRuleFromServiceObject(triggerActuatorSensorRuleServiceObject);
	}
	
	buildTriggerActuatorSensorRuleServiceObject(triggerActuatorSensorRule) {
		return this.triggerActuatorSensorRuleMapper.buildTriggerActuatorSensorRuleServiceObject(triggerActuatorSensorRule);
	}
	
	#buildCameraSensorRulesFromServiceObject(cameraSensorRulesServiceObject) {
		var cameraSensorRules = [];
		cameraSensorRulesServiceObject.forEach(cameraSensorRuleServiceObject => {
			var cameraSensorRule = this.buildCameraSensorRuleFromServiceObject(cameraSensorRuleServiceObject);
			cameraSensorRules.push(cameraSensorRule);
		})
		return cameraSensorRules;
	}
	
	buildCameraSensorRuleFromServiceObject(cameraSensorRuleServiceObject) {
		 return this.cameraSensorRuleMapper.buildCameraSensorRuleFromServiceObject(cameraSensorRuleServiceObject);
	}
	
	
	buildCameraSensorRuleServiceObject(cameraSensorRule) {
		return this.cameraSensorRuleMapper.buildCameraSensorRuleServiceObject(cameraSensorRule);
	}
	
	buildAlarmSensorRuleServiceObject(alarmSensorRule) {
		return this.alarmSensorRuleMapper.buildAlarmSensorRuleServiceObject(alarmSensorRule);
	}
	
	buildEnableSensorRuleServiceObject(enableSensorRule) {
		return this.enableSensorRuleMapper.buildEnableSensorRuleServiceObject(enableSensorRule);
	}
		
	
}

	