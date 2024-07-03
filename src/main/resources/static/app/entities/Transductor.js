class Transductor extends Device{
	
	constructor(name, description, active, properties, watchdogInterval, watchdogEnabled) {
		super(name, description, active, watchdogInterval, watchdogEnabled);
		this.properties = properties;
	}
	
	getProperties() {
		return this.properties;
	}
	
	
	
	
}