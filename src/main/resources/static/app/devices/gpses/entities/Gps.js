class Gps extends Device{
	
	constructor(name, description, active, watchdogInterval, watchdogEnabled, longitude, latitude) {
		super(name, description, active, watchdogInterval, watchdogEnabled);
		this.longitude = longitude;
		this.latitude = latitude;
	}
		
	getLongitude() {
		return this.longitude;
	}
	
	getLatitude() {
		return this.latitude;
	}
	
}