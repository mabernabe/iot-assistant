class Transductor extends Device{
	
	constructor(name, description, properties, watchdogInterval, watchdogEnabled) {
		super(name, description, watchdogInterval, watchdogEnabled);
		this.properties = properties;
	}
	
	getProperties() {
		return this.properties;
	}
	
	
	
	
}