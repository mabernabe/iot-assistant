class IotAssistantCapabilities {

	constructor(devicesCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected) {
		if(!arguments.length) {
			this.devicesCapabilities = new devicesCapabilities([], [], []);
			this.mqttInterfaceCapabilities = new MqttInterfaceCapabilities("", false, "");
			this.chartCapabilities = new ChartCapabilities([]);
			this.cameraCapabilities = new CameraCapabilities([], []);
			this.notificationsCapabilities = new NotificationsCapabilities([]);
			this.ruleCapabilities = new RuleCapabilities([]);
			this.telegramConnected = false;
        }
		else {
			this.devicesCapabilities = devicesCapabilities;
			this.mqttInterfaceCapabilities = mqttInterfaceCapabilities;
			this.telegramConnected = isTelegramConnected;
			this.ruleCapabilities = ruleCapabilities;
			this.chartCapabilities = chartCapabilities;
			this.cameraCapabilities = cameraCapabilities;
			this.notificationsCapabilities = notificationsCapabilities;
		}
	}

	getTransductorInterfacesCapabilities() {
		return [this.mqttInterfaceCapabilities];
	}


	getSensorSupportedProperties() {
		return this.devicesCapabilities.getSensorSupportedProperties();
	}
	
	getSensorSupportedInterfaces() {
		return this.devicesCapabilities.getSensorSupportedInterfaces();
	}
	
	getSensorSupportedWatchdogIntervals() {
		return this.devicesCapabilities.getSensorSupportedWatchdogIntervals();
	}
	
	getActuatorSupportedWatchdogIntervals() {
		return this.devicesCapabilities.getActuatorSupportedWatchdogIntervals();
	}
	
	getActuatorSupportedProperties() {
		return this.devicesCapabilities.getActuatorSupportedProperties();
	}

	getActuatorSupportedInterfaces() {
		return this.devicesCapabilities.getActuatorSupportedInterfaces();
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
	
	getSensorRulesCapabilities() {
	 	return this.ruleCapabilities;
	}	
	
	getSupportedNotificationsTypes() {
		return this.notificationsCapabilities.getSupportedNotificationsTypes();
	}
	
	isTelegramConnected() {
	    return this.telegramConnected;
	}

}