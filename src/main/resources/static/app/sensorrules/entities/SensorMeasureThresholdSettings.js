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

	isSensorPropertyBinary() {
		return this.sensorProperty.isBinary();
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
		if (allValuesAreSet && this.sensorProperty.isBinary() && this.sensorValueThreshold != 'true' && this.sensorValueThreshold != 'false') {
			valueThresholdIsValid = false;
		}
		var analogThresholdOperatorIsValid = true;
		if (allValuesAreSet && !this.sensorProperty.isBinary() && typeof this.sensorAnalogThresholdOperator === 'undefined') {
			valueThresholdIsValid = false;
		}		
		return allValuesAreSet && valueThresholdIsValid && analogThresholdOperatorIsValid;
	}
	
	
	getSensorValueThresholdString() {
		if (this.sensorProperty.isBinary()) {
			return this.sensorValueThreshold == 'true' ? "Active" : "Inactive";
		}
		return this.sensorValueThreshold;
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