package com.iotassistant.models.pininterface;

public enum PinCapability {
	DIGITAL(true, false),
	ANALOG(false, true),
	ANALOG_DIGITAL(true,true);
	
	boolean digital;
	
	boolean analog;

	private PinCapability(boolean digital, boolean analog) {
		this.digital = digital;
		this.analog = analog;
	}

	public boolean isDigital() {
		return digital; 
	}
	
}
