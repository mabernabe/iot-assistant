class Device{
	
	constructor(name, description, active, watchdogInterval, watchdogEnabled) {
		this.name = name;
		this.description = description;
		this.active = active;
		this.watchdogInterval = watchdogInterval;
		this.watchdogEnabled = watchdogEnabled;
	}
	
	getName () {
		return this.name;
	}
	
	getDescription () {
		return this.description;
	}
	
	isActive() {
		return this.active;
	}
	
	getWatchdogInterval () {
		return this.watchdogInterval;
	}
	
	hasWatchdog () {
		return (this.getWatchdogInterval().toUpperCase() !== 'DONT WATCH');
	}
	
	isWatchdogEnabled () {
		return this.hasWatchdog() && this.watchdogEnabled;
	}
	
}