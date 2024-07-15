class TransductorValue {
	
	constructor(string, unit) {
		this.string = string;
		this.unit = unit;
	}
	
	getString() {
		return this.string;
	}
	
	getStringWithUnit() {
		return this.string + ' ' + this.unit;
	}
	
	isHigh() {
		return this.string === "true";	
	}
	
	static getStringFromBinaryValue(value) {
		if (value) {
			return "true";
		} else {
			return "false";
		}
	}
	
	static getDigitalIntValue(value) {
		if (value === "true") {
			return 1;
		} else {
			return 0;
		}
		
	}
	

}


