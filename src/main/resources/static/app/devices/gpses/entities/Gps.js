class Gps extends Device{
	
	constructor(name, description, active, watchdogInterval, watchdogEnabled, position) {
		super(name, description, active, watchdogInterval, watchdogEnabled);
		this.position = position;
	}
		
	getLongitude() {
		return this.position.getLongitude();
	}
	
	getLatitude() {
		return this.position.getLatitude();
	}
	
	getPositionDate() {
		return this.position.getDate();
	}
	
}