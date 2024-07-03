class Property {
	
	constructor(name, unit, isDigital, minimumValue, maximumValue) {
		this.name = name;
		this.unit = unit;
		this.digital = isDigital;
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

	
	isDigital () {
		return this.digital;
	}
	
	getMaximumValue(){
		return this.maximumValue;
	}

	getMinimumValue() {
		return this.minimumValue;
	}
	
}

