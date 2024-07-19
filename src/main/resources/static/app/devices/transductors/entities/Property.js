class Property {
	
	constructor(name, nameWithUnit, unit, binary, minimumValue, maximumValue) {
		this.name = name;
		this.nameWithUnit = nameWithUnit;
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
		return this.nameWithUnit;
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

