class SensorRule {
	
	
	constructor(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType) {
		this.sensorMeasureThresholdSettings = sensorMeasureThresholdSettings;
		this.type = type;
		this.id = id;		
		this.enabled = enabled;
		this.timeBetweenTriggers = timeBetweenTriggers;
		this.notificationType = notificationType;
	}

	getSensorName() {
		return this.sensorMeasureThresholdSettings.getSensorName();
	}
	
	
	getSensorAnalogThresholdOperator() {
		return this.sensorMeasureThresholdSettings.getSensorAnalogThresholdOperator();
	}
	
	setSensorAnalogThresholdOperator(analogThresholdOperator) {
		return this.sensorMeasureThresholdSettings.setSensorAnalogThresholdOperator(analogThresholdOperator);
	}
	
	
	getSensorValueThreshold() {
		return this.sensorMeasureThresholdSettings.getSensorValueThreshold();
	}
	

	getSensorPropertyMinimumValue() {
		return this.sensorMeasureThresholdSettings.getSensorPropertyMinimumValue();
	}
	
	getSensorPropertyMaximumValue() {
		return this.sensorMeasureThresholdSettings.getSensorPropertyMaximumValue();
	}
	
	
	isSensorPropertyBinary() {
		return this.sensorMeasureThresholdSettings.isSensorPropertyBinary();
	}
	
	getSensorProperty() {
		return this.sensorMeasureThresholdSettings.getSensorProperty() ;
	}
	
	setSensorProperty(sensorProperty) {
		this.sensorMeasureThresholdSettings.setSensorProperty(sensorProperty);
	}
	
	
	getSensorPropertyName() {
		return this.sensorMeasureThresholdSettings.getSensorPropertyName() ;
	}

		
	setType(type) {
		this.type = type;
	}
	
	getType() {
		return this.type;
	}
	
	
	isValid() {
		var stateIsValid = false;
		if (this.type != null && this.enabled != null && this.notificationType != null 
			&&  this.sensorMeasureThresholdSettings.isValid() && this.timeBetweenTriggers != null) {
			stateIsValid = true;
		}
		return stateIsValid;
	}
		
	
	setId(id) {
		this.id = id;
	}
	
	getId() {
		return this.id;
	}

	
	getTimeBetweenTriggers() {
		return this.timeBetweenTriggers;
	}
	
	setTimeBetweenTriggers(timeBetweenTriggers) {
		this.timeBetweenTriggers = timeBetweenTriggers;
	}
	
	getNotificationType() {
		return this.notificationType;
	}
	
	
	isEnabled() {
		return this.enabled;
	}
	
	setEnabled(enabled) {
	 	this.enabled = enabled;
	}
	

	
}