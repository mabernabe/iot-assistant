class MqttGps{
	
	constructor() {
		this.name = null;
		this.description = null;
		this.interfaceType = null;
		this.watchdogInterval = null;
	}
	
	getName () {
		return this.name;
	}
	
	getDescription () {
		return this.description;
	}
	
	setName(name) {
		this.name = name;
	}
	
	setDescription (description) {
		this.description = description;
	}
	
	getInterfaceType() {
		return this.interfaceType;
	}
	
	setInterfaceType(interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	interfaceTypeIsHTTP() {
		return this.interfaceType === "HTTP";
	}
	
	
	getWatchdogInterval () {
		return this.watchdogInterval;
	}
	
	setWatchdogInterval (watchdogInterval) {
		this.watchdogInterval = watchdogInterval;
	}
	
	isValid() {
		return this.getName() != null && this.getDescription() != null 
		&& this.getInterfaceType() != null && this.getWatchdogInterval() != null;
	}
	
	
}