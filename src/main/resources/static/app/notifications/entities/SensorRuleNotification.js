class  SensorRuleNotification{
	
	
	constructor(id, sensorRule, sensorValue, date) {
		this.id = id;
		this.sensorRule = sensorRule;
		this.sensorValue = sensorValue;
		this.date = date;
	}

	toString() {
		var sensorValue = this.sensorValue;
		var sensorRule = this.sensorRule;
		var sensorValue = this.sensorValue
		var analogThresholdOperator = sensorRule.isSensorPropertyBinary() ? '' : sensorRule.getSensorAnalogThresholdOperator();
		var text =  "#" + sensorRule.getId() + " Notification: "  + ": " + "Sensor " + sensorRule.getSensorName() + " with property " 
		+ sensorRule.getSensorPropertyName() +  " reached threshold " + analogThresholdOperator + sensorRule.getSensorValueThreshold() + ". Sensor value was " + sensorValue
		+ ". Date: " + this.date;
		return text;
	}
	
	getId() {
		return this.id;
	}

	
}