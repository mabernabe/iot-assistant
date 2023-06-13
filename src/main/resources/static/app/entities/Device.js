class Device{
	
	constructor(name, description, watchdogInterval, watchdogEnabled) {
		this.name = name;
		this.description = description;
		this.watchdogInterval = watchdogInterval;
		this.watchdogEnabled = watchdogEnabled;
	}
	
	getName () {
		return this.name;
	}
	
	getDescription () {
		return this.description;
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