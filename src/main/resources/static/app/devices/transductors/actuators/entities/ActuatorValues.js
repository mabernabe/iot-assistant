class ActuatorValues {
	
	constructor(date, values) {
		this.date = date;
		this.values = values;
	}
	
	
	getDate() {
		return this.date;
	}
	
	
	getValue(propertyActuated) {
		return this.values[propertyActuated];
	}
	
	
}