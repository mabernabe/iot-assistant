package com.iotassistant.models.transductormqttinterface;

public enum MqttLWTMessageValue {
	OFFLINE("OFFLINE");

	private String string;
	
	MqttLWTMessageValue(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	

}
