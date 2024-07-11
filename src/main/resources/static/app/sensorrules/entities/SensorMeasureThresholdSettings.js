class SensorMeasureThresholdSettings {
	
	
	constructor() {
		this.sensorName;
		this.sensorProperty;
		this.sensorValueThreshold;
		this.sensorAnalogThresholdOperator;
	}

	getSensorName() {
		return this.sensorName;
	}
	
	getSensorAnalogThresholdOperator() {
		return this.sensorAnalogThresholdOperator;
	}
	
	setSensorName(sensorName) {
		this.sensorName = sensorName;
	}
	
	getSensorProperty() {
		return this.sensorProperty ;
	}
	
	setSensorProperty(sensorProperty) {
		this.sensorProperty = sensorProperty;
	}
	
	getSensorPropertyMinimumValue() {
		return this.sensorProperty.getMinimumValue();
	}
	
	getSensorPropertyMaximumValue() {
		return this.sensorProperty.getMaximumValue();
	}

	isSensorPropertyDigital() {
		return this.sensorProperty.isDigital();
	}
	
	getSensorPropertyName() {
		if (this.sensorProperty == null || typeof this.sensorProperty === 'undefined') {
			return this.sensorProperty;
		}
		return this.sensorProperty.getName() ;
	}

	
	isValid() {
		var allValuesAreSet = false;
		if (this.sensorName !== 'undefined' && typeof this.sensorProperty !== 'undefined' && typeof this.sensorValueThreshold !== 'undefined') {
			allValuesAreSet = true;
		}
		var valueThresholdIsValid = true;
		if (allValuesAreSet && this.sensorProperty.isDigital() && this.sensorValueThreshold != 'High' && this.sensorValueThreshold != 'Low') {
			valueThresholdIsValid = false;
		}
		var analogThresholdOperatorIsValid = true;
		if (allValuesAreSet && !this.sensorProperty.isDigital() && typeof this.sensorAnalogThresholdOperator === 'undefined') {
			valueThresholdIsValid = false;
		}		
		return allValuesAreSet && valueThresholdIsValid && analogThresholdOperatorIsValid;
	}
	
	getSensorValueThreshold() {
		return this.sensorValueThreshold;
	}
	
	setSensorValueThreshold(valueThreshold) {
		this.sensorValueThreshold = valueThreshold;
	}
	
	
	getSensorAnalogThresholdOperator() {
		return this.sensorAnalogThresholdOperator;
	}
	
	setSensorAnalogThresholdOperator(analogThresholdOperator) {
		this.sensorAnalogThresholdOperator = analogThresholdOperator;
	}
	
}