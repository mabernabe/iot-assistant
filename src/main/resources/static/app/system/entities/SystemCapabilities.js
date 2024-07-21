class SystemCapabilities {

	constructor(devicesCapabilities, serversStatus, chartCapabilities, notificationsCapabilities, ruleCapabilities) {
		this.devicesCapabilities = devicesCapabilities;
		this.serversStatus = serversStatus;
		this.ruleCapabilities = ruleCapabilities;
		this.chartCapabilities = chartCapabilities;
		this.notificationsCapabilities = notificationsCapabilities;
		
	}

	getServersStatus() {
		return this.serversStatus;
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

	
	getSupportedChartTypes() {
		return this.chartCapabilities.getSupportedChartTypes();
	}
	
	getSupportedChartIntervals() {
		return this.chartCapabilities.getSupportedChartIntervals();
	}
	
	getSupportedSampleIntervals() {
		return this.chartCapabilities.getSupportedSampleIntervals();
	}
	
	
	getSensorRulesCapabilities() {
	 	return this.ruleCapabilities;
	}	
	
	getSupportedNotificationsTypes() {
		return this.notificationsCapabilities.getSupportedNotificationsTypes();
	}

}