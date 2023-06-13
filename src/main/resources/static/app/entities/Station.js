class Station {

	constructor(stationCapabilities, platformName) {
		this.capabilities = stationCapabilities;
		this.platformName = platformName;
    }
	
	getCapabilities() {
		return this.capabilities;
	}
	
	getPlatformName() {
		return this.platformName;
	}
	
	getTransductorInterfacesCapabilities() {
		return this.capabilities.getTransductorInterfacesCapabilities();
	}	
	
	isTelegramConnected() {
		return this.capabilities.isTelegramConnected();
	}

}