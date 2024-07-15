class Property {
	
	constructor(name, unit, binary, minimumValue, maximumValue) {
		this.name = name;
		this.unit = unit;
		this.binary = binary;
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}
	
	getName () {
		return this.name;
	}
	
	getUnit () {
		return this.unit;
	}

	getNameWithUnit () {
		return this.unit != null ? this.name + " " + this.unit : this.name;
	}

	
	isBinary () {
		return this.binary;
	}
	
	getMaximumValue(){
		return this.maximumValue;
	}

	getMinimumValue() {
		return this.minimumValue;
	}
		
	
}

