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
	

}


