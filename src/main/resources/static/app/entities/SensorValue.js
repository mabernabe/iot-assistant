	
class SensorValue {
	
	constructor(string, unit, description, severity) {
		this.string = string;
		this.unit = unit;
		this.description = description;
		this.severity = severity;
	}
	
	
	getString() {
		return this.string;
	}
	
	getStringWithUnit() {
		return this.string + this.unit;
	}
	
	hasDescription() {
		return this.description != null;
	}
	
	
	getDescription() {
		return this.description;
	}
	
	
	getSeverity() {
		return this.severity;
	}
	
}