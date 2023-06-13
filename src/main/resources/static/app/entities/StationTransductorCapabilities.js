class StationTransductorCapabilities {

	constructor(supportedProperties, supportedInterfaces, supportedWatchdogIntervals, supportedRulesTypes) {
		this.supportedProperties = supportedProperties;
		this.supportedInterfaces = supportedInterfaces;
		this.supportedWatchdogIntervals = supportedWatchdogIntervals;
		this.supportedRulesTypes = supportedRulesTypes;
		this.supportedRulesTypes = supportedRulesTypes;
	}


	getSupportedProperties() {
		return this.supportedProperties;
	}

	getSupportedInterfaces() {
		return this.supportedInterfaces;
	}
	
	getSupportedWatchdogIntervals() {
		return this.supportedWatchdogIntervals;
	}
	
	getSupportedRulesTypes() {
		return this.supportedRulesTypes;
	}
	
	getSensorRuleTimeBetweenTriggers() {
		return this.supportedRulesTimeBetweenTriggers;
	}

}