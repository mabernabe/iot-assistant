class System {

	constructor(systemCapabilities, platformName, uptime) {
		this.capabilities = systemCapabilities;
		this.platformName = platformName;
		this.uptime = uptime;
    }
	
	getCapabilities() {
		return this.capabilities;
	}
	
	getPlatformName() {
		return this.platformName;
	}
	
	getUptime() {
		return this.uptime;
	}
	
	getServersStatus() {
		return this.capabilities.getServersStatus();
	}

}