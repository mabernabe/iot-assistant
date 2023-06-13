package com.iotassistant.models.mqttinterface;

public interface MqttPlatformInterfaceClient {
	
	public void messageArrived(String topic, String message);
	
	public void connectionLost();

	public void connectComplete();

}
