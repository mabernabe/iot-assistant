class TriggerActuatorSensorRule extends SensorRule{
		
	constructor(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, actuatorSettings) {
		super(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType);
		this.actuatorSettings = actuatorSettings;
	}
	
	getActuatorName() {
		return this.actuatorSettings.getActuatorName();
	}
	
	getActuatorPropertyMinimumValue() {
		return this.actuatorSettings.getActuatorPropertyMinimumValue();
	}
	
	getActuatorPropertyMaximumValue() {
		return this.actuatorSettings.getActuatorPropertyMaximumValue();
	}
	
	isActuatorPropertyBinary() {
		return this.actuatorSettings.isActuatorPropertyBinary();
	}
	
	getActuatorProperty() {
		return this.actuatorSettings.getActuatorProperty() ;
	}
	
	getActuatorPropertyName() {
		return this.actuatorSettings.getActuatorPropertyName() ;
	}
	
	setActuatorProperty(actuatorProperty) {
		this.actuatorSettings.setActuatorProperty(actuatorProperty);
	}
	
		
	getActuatorPropertyName() {
		return this.actuatorSettings.getActuatorPropertyName() ;
	}
	
	getActuatorSetValue() {
		return this.actuatorSettings.getActuatorSetValue();
	}
	
	getActuatorSetValueString() {
		return this.actuatorSettings.getActuatorSetValueString();
	}
	
	setActuatorSetValue(actuatorSetValue) {
		this.actuatorSettings.setActuatorSetValue(actuatorSetValue);
	}
	
	isValid() {
		return super.isValid() && this.actuatorSettings.isValid();
	}
	
	static get triggerActuatorSensorRuleType() {
    	return 'Trigger actuator';
  	}

	accept(sensorRuleVisitor) {
		sensorRuleVisitor.visitTriggerActuatorSensorRule(this);
	}
	
}