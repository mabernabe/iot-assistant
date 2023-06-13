class TransductorInterfaceCapabilities {

	constructor(interfaceName, available, details) {
		this.interfaceName = interfaceName;
		this.available = available;
		this.details = details;
	}

	getInterfaceName() {
		return this.interfaceName;
	}

	isAvailable() {
		return this.available;
	}
	
	getDetails() {
		return this.details;
	}


}