class CameraCapabilities {
	constructor(supportedInterfaces, supportedWatchdogIntervals) {
		this.supportedInterfaces = supportedInterfaces;
		this.supportedWatchdogIntervals = supportedWatchdogIntervals;
	}

	getSupportedInterfaces() {
		return this.supportedInterfaces;
	}
	
	getSupportedWatchdogIntervals() {
		return this.supportedWatchdogIntervals;
	}
}