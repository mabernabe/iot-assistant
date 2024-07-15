	
class SensorValue extends TransductorValue{
	
	constructor(string, unit, description, severity) {
		super(string, unit);
		this.description = description;
		this.severity = severity;
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