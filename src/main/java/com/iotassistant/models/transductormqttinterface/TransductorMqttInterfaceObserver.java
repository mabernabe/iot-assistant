package com.iotassistant.models.transductormqttinterface;

public interface TransductorMqttInterfaceObserver {
	
	public boolean messageArrived(String topic, String message);

	public void lwtMessageArrived(String message);
}
