class CameraSensorRule extends SensorRule{
		
	constructor(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType, cameraName) {
		super(sensorMeasureThresholdSettings, type, id, enabled, timeBetweenTriggers, notificationType);
		this.cameraName = cameraName;
	}
	
	getCameraName() {
		return this.cameraName;
	}
	
	setCameraName(cameraName) {
		this.cameraName = cameraName;
	}
	
	isValid() {
		return super.isValid() && this.cameraName != null;
	}
	
	static get cameraSensorRuleType() {
    	return 'Take camera picture';
  	}

	accept(sensorRuleVisitor) {
		sensorRuleVisitor.visitTriggerActuatorSensorRule(this);
	}
	
}