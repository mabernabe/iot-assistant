class TransductorValue {
	
	constructor(property, value, unit, date) {
		this.property = property;
		this.value = value;
		this.unit = unit;
		this.date = date;
	}

	getProperty () {
		return this.property;
	}
	
	getValue() {
		return this.value;
	}

	getValueWithUnit () {
		var value = this.getValue();
		if (this.unit != null) {
			value += ' ' + this.unit;
		}
		return value ;
	}
	
	getDate(){
		return this.date;
	
	}
	
	isDigital () {
		return (this.unit == null);
	}
	
	isHigh () {
		return (this.getValue() == "High");
	}
	
	setValue(value) {
		this.value = value;
	}
	
	static getDigitalStringValue(value) {
		if (value) {
			return "High";
		} else {
			return "Low";
		}
	}
	
	static getDigitalIntValue(value) {
		if (value === "High") {
			return 1;
		} else {
			return 0;
		}
		
	}


}


