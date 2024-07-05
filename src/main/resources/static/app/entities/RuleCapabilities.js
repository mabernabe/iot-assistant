class RuleCapabilities {

	constructor(supportedSensorRulesTypes, supportedSensorRulesTimeBetweenTriggers) {
		this.supportedSensorRulesTypes = supportedSensorRulesTypes;
		this.supportedSensorRulesTimeBetweenTriggers = supportedSensorRulesTimeBetweenTriggers;
	}

	getSupportedSensorRulesTypes() {
		return this.supportedSensorRulesTypes;
	}
	
	getSupportedSensorRulesTimeBetweenTriggers() {
		return this.supportedSensorRulesTimeBetweenTriggers;
	}
	
}