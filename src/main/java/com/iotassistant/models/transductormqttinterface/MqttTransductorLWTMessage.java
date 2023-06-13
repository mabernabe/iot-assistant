package com.iotassistant.models.transductormqttinterface;

public class MqttTransductorLWTMessage {
	
	private MqttLWTMessageValue value;

	public MqttTransductorLWTMessage(MqttLWTMessageValue value) {
		super();
		this.value = value;
	}

	public MqttLWTMessageValue getValue() {
		return value;
	}

	public void setValue(MqttLWTMessageValue value) {
		this.value = value;
	}
	
	

}
