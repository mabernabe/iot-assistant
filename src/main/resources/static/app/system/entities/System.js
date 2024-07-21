class System {

	constructor(systemCapabilities, platformName) {
		this.capabilities = systemCapabilities;
		this.platformName = platformName;
    }
	
	getCapabilities() {
		return this.capabilities;
	}
	
	getPlatformName() {
		return this.platformName;
	}
	
	getServersStatus() {
		return this.capabilities.getServersStatus();
	}

}