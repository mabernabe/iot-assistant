class SensorValues {
	
	constructor(date, values) {
		this.date = date;
		this.values = values;
	}
	
	
	getDate() {
		return this.date;
	}
	
	
	getValue(propertyMeasured) {
		return this.values[propertyMeasured];
	}
	
	
	getPropertyMeasured() {
		return this.propertyMeasured;
	}
	
	
	getValueWithUnit() {
		return this.value + " " + this.propertyMeasured.getUnit();
	}
	
}