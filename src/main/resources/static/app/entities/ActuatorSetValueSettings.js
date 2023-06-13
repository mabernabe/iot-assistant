class ActuatorSetValueSettings {
	
	
	constructor() {
		this.actuatorName;
		this.actuatorProperty;
		this.actuatorSetValue;
	}

	getActuatorName() {
		return this.actuatorName;
	}
	
	setActuatorName(name) {
		this.actuatorName = name;
	}
	
	
	getActuatorProperty() {
		return this.actuatorProperty ;
	}
	
	setActuatorProperty(actuatorProperty) {
		this.actuatorProperty = actuatorProperty;
	}
	
	
	getActuatorPropertyName() {
		if (this.actuatorProperty == null || typeof this.actuatorProperty === 'undefined') {
			return this.actuatorProperty;
		}
		return this.actuatorProperty.getName() ;
	}
	
	
	isValid() {
		var allValuesAreSet = false;
		if (typeof this.actuatorName !== 'undefined' && typeof this.actuatorProperty !== 'undefined' && typeof this.actuatorSetValue !== 'undefined') {
			allValuesAreSet = true;
		}
		var setValueIsValid = true;
		if (allValuesAreSet && this.actuatorProperty.isDigital() && this.actuatorSetValue != 'High' && this.actuatorSetValue != 'Low') {
			setValueIsValid = false;
		}		
		return allValuesAreSet && setValueIsValid;
	}
	
	getActuatorPropertyMinimumValue() {
		return this.actuatorProperty.getMinimumValue();
	}
	
	getActuatorPropertyMaximumValue() {
		return this.actuatorProperty.getMaximumValue();
	}
	
	
	getActuatorSetValue() {
		return this.actuatorSetValue;
	}
	
	setActuatorSetValue(value) {
		this.actuatorSetValue = value;
	}
	
	isActuatorPropertyDigital() {
		return this.actuatorProperty.isDigital();
	}
	
	
	
}