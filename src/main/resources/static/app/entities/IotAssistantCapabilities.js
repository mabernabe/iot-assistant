class IotAssistantCapabilities {

	constructor(sensorCapabilities, actuatorCapabilities, pinInterfaceCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected) {
		if(!arguments.length) {
			this.sensorCapabilities = new IotAssistantTransductorCapabilities([], [], []);
			this.actuatorCapabilities = new IotAssistantTransductorCapabilities([], [], []);
			this.mqttInterfaceCapabilities = new IotAssistantMqttInterfaceCapabilities("", false, "");
			this.chartCapabilities = new IotAssistantChartCapabilities([]);
			this.cameraCapabilities = new IotAssistantCameraCapabilities([], []);
			this.notificationsCapabilities = new IotAssistantNotificationsCapabilities([]);
			this.ruleCapabilities = new IotAssistantRuleCapabilities([]);
			this.telegramConnected = false;
        }
		else {
			this.sensorCapabilities = sensorCapabilities;
			this.actuatorCapabilities = actuatorCapabilities;
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