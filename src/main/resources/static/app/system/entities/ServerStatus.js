class ServerStatus{

	constructor(interfaceName, connected, details) {
		this.interfaceName = interfaceName;
		this.connected = connected;
		this.details = details;
	}
	
	getInterfaceName() {
		return this.interfaceName;
	}

	isConnected() {
		return this.connected;
	}
	
	getDetails() {
		return this.details;
	}
	


}