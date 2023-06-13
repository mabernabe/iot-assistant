class SensorRuleAlarmNotification extends SensorRuleNotification{
	
	
	constructor(id, sensorRule, sensorValue, date) {
		super(id, sensorRule, sensorValue, date);
	}

	toString() {
		var text = "Sensor Alarm " + super.toString();
		return text;
		
	}

	
}