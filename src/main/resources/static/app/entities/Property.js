class Property {
	
	constructor(name, shortName, isDigital, minimumValue, maximumValue) {
		this.name = name;
		this.shortName = shortName;
		this.digital = isDigital;
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}

	getShortName () {
		return this.shortName;
	}

	getName () {
		return this.name;
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

