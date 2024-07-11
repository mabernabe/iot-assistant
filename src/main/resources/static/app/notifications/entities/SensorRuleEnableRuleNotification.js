class SensorRuleEnableRuleNotification extends SensorRuleNotification{
	
	
	constructor(id, sensorRule, sensorValue, date, newSensorRuleState) {
		super(id, sensorRule, sensorValue, date);
		this.newSensorRuleState = newSensorRuleState;
	}

	toString() {
		var text = "Enable Sensor Rule " + super.toString();
		var newSensorRuleState = this.newSensorRuleState == false ? 'Disabled' : 'Enabled';
		text += ". New Sensor Rule state : " + newSensorRuleState;
		return text;
	}

	
}