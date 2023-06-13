class StationCapabilities {

	constructor(sensorCapabilities, actuatorCapabilities, pinInterfaceCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected) {
		if(!arguments.length) {
			this.sensorCapabilities = new StationTransductorCapabilities([], [], []);
			this.actuatorCapabilities = new StationTransductorCapabilities([], [], []);
			this.pinInterfaceCapabilities = new StationPinInterfaceCapabilities("", false, [], [], "");
			this.mqttInterfaceCapabilities = new StationMqttInterfaceCapabilities("", false, "");
			this.chartCapabilities = new StationChartCapabilities([]);
			this.cameraCapabilities = new StationCameraCapabilities([], []);
			this.notificationsCapabilities = new StationNotificationsCapabilities([]);
			this.ruleCapabilities = new StationRuleCapabilities([]);
			this.telegramConnected = false;
        }
		else {
			this.sensorCapabilities = sensorCapabilities;
			this.actuatorCapabilities = actuatorCapabilities;
			this.pinInterfaceCapabilities = pinInterfaceCapabilities;
			this.mqttInterfaceCapabilities = mqttInterfaceCapabilities;
			this.telegramConnected = isTelegramConnected;
			this.ruleCapabilities = ruleCapabilities;
			this.chartCapabilities = chartCapabilities;
			this.cameraCapabilities = cameraCapabilities;
			this.notificationsCapabilities = notificationsCapabilities;
		}
	}

	getTransductorInterfacesCapabilities() {
		return [this.pinInterfaceCapabilities, this.mqttInterfaceCapabilities];
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
	
	getSensorSupportedRulesTypes() {
		return this.sensorCapabilities.getSupportedRulesTypes();
	}
	
	getSupportedSensorRuleTimeBetweenTriggers() {
		return this.sensorCapabilities.getSensorRuleTimeBetweenTriggers();
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

	getAvailableDigitalPinIds() {
		return this.pinInterfaceCapabilities.getAvailableDigitalPinIds;
	}


	getAvailableAnalogPinIds() {
		return this.pinInterfaceCapabilities.getAvailableAnalogPinIds;
	}

	isSensorPinAvailable(propertyName) {
		var availablePins = this.getAvailablePinIds(propertyName, this.sensorCapabilities.getSupportedProperties());
		return availablePins.length > 0;
	}

	getSensorAvailablePinIds(propertyName) {
		return this.getAvailablePinIds(propertyName, this.sensorCapabilities.getSupportedProperties());
	}

	getAvailablePinIds(propertyName, properties) {
		var isPropertyDigital = false;
		properties.forEach(property => {
			if (property.getName() === propertyName && property.isDigital()) {
				isPropertyDigital = true;
			}
		})
		return this.pinInterfaceCapabilities.getAvailablePinIds(isPropertyDigital);
	}
	
	isActuatorPinAvailable(propertyName) {
		var availablePins = this.getAvailablePinIds(propertyName, this.actuatorCapabilities.getSupportedProperties());
		return availablePins.length > 0;
	}

	getActuatorAvailablePinIds(propertyName) {
		return this.getAvailablePinIds(propertyName, this.actuatorCapabilities.getSupportedProperties());
	}
	
	getPlatformPinInterfaceName() {
		return this.pinInterfaceCapabilities.getPlatformPinInterfaceName();
	}
	
	
	getMqttInterfaceCapabilities() {
		return this.mqttInterfaceCapabilities;
	}
	
	getSupportedChartTypes() {
		return this.chartCapabilities.getSupportedChartTypes();
	}
	
	getSupportedChartIntervals() {
		return this.chartCapabilities.getSupportedChartIntervals();
	}
	
	getSupportedSampleIntervals() {
		return this.chartCapabilities.getSupportedSampleIntervals();
	}
	
	getCameraCapabilities() {
		return this.cameraCapabilities;
	}
	
	getStationSensorRulesCapabilities() {
	 	return this.ruleCapabilities;
	}	
	
	getSupportedNotificationsTypes() {
		return this.notificationsCapabilities.getSupportedNotificationsTypes();
	}
	
	isTelegramConnected() {
	    return this.telegramConnected;
	}

}