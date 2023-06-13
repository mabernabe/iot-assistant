class SensorRuleCameraNotification extends SensorRuleNotification{
	
	
	
	constructor(id, sensorRule, sensorValue, date, pictureURL) {
		super(id, sensorRule, sensorValue, date);
		this.pictureURL = pictureURL;
	}

	toString() {
		var text = "Camera Sensor Rule " + super.toString();
		return text;
	}
	
	hasAttachment() {
		return true;
	}
	
	getAttachment() {
		return this.pictureURL;
	}

	
}