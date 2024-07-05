class DevicesCapabilities {

	constructor(sensorCapabilities, actuatorCapabilities, cameraCapabilities) {
		this.sensorCapabilities = sensorCapabilities;
		this.actuatorCapabilities = actuatorCapabilities;
		this.cameraCapabilities = cameraCapabilities;
	}

	getSensorSupportedProperties() {
		return this.sensorCapabilities.getSupportedProperties();
	}
	
	getSensorSupportedInterfaces() {
		return this.sensorCapabilities.getSupportedInterfaces();
	}
	
	getSensorSupportedWatchdogIntervals() {
		return this.sensorCapabilities.getSupportedWatchdogIntervals();
	}
	
	getActuatorSupportedWatchdogIntervals() {
		return this.actuatorCapabilities.getSupportedWatchdogIntervals();
	}
	
	getActuatorSupportedProperties() {
		return this.actuatorCapabilities.getSupportedProperties();
	}

	getActuatorSupportedInterfaces() {
		return this.actuatorCapabilities.getSupportedInterfaces();
	}
	
	getCameraSupportedInterfaces() {
		return this.cameraCapabilities.getSupportedInterfaces();
	}
	
	getCameraSupportedWatchdogIntervals() {
		return this.cameraCapabilities.getSupportedWatchdogIntervals();
	}

}