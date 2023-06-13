class Measure extends TransductorValue {
	
	constructor(propertyMeasured, value, unit, date, severity, description) {
		super(propertyMeasured, value, unit, date)
		this.severity = severity;
		this.description = description;
	}
	
	getSeverity() {
		return this.severity;
	}
	
	getValueDescription () {
		return this.description;
	}

}


