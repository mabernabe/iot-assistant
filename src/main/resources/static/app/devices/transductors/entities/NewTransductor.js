class NewTransductor{
	
	constructor() {
		this.name = null;
		this.description = null;
		this.propertiesNames = [];
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
	
	getPropertiesNames () {
		return this.propertiesNames;
	}
	
	setPropertiesNames(propertiesNames) {
		this.propertiesNames = propertiesNames;
	}
	
	getInterfaceType() {
		return this.interfaceType;
	}
	
	setInterfaceType(interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	
	interfaceTypeIsMQTT() {
		return this.interfaceType === "MQTT";
	}
	
	
	getWatchdogInterval () {
		return this.watchdogInterval;
	}
	
	setWatchdogInterval (watchdogInterval) {
		this.watchdogInterval = watchdogInterval;
	}
	
}