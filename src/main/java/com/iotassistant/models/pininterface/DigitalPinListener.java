package com.iotassistant.models.pininterface;

public interface DigitalPinListener {
	
	public void notify(boolean digitalValue);
	
	public PinId getPinId();

}
