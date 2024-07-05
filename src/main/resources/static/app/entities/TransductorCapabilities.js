class TransductorCapabilities {

	constructor(supportedProperties, supportedInterfaces, supportedWatchdogIntervals) {
		this.supportedProperties = supportedProperties;
		this.supportedInterfaces = supportedInterfaces;
		this.supportedWatchdogIntervals = supportedWatchdogIntervals;
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
	
}