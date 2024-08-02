class TransductorCapabilities extends DeviceCapabilities{

	constructor(supportedProperties, supportedInterfaces, supportedWatchdogIntervals) {
		super(supportedInterfaces, supportedWatchdogIntervals);
		this.supportedProperties = supportedProperties;
	}


	getSupportedProperties() {
		return this.supportedProperties;
	}
}