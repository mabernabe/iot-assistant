package com.iotassistant.models.devices.transductors.propertyactuated;

class BinaryLED extends BinaryPropertyActuated{
	
	private static final String NAME = "Led";

	@Override
	public String getName() {
		return NAME;
	}

}
