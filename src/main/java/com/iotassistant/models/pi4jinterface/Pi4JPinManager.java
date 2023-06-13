package com.iotassistant.models.pi4jinterface;

import java.util.Collection;

import com.iotassistant.models.pininterface.PinId;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;

public abstract class Pi4JPinManager {

	
	protected void unprovisionPi4jPin(Pin pi4jPin) {
		Collection<GpioPin> provisionedGpioPinsIterator = GpioFactory.getInstance().getProvisionedPins();
		GpioPin gpioProvisionedPin = null;
		for (GpioPin gpioPin : provisionedGpioPinsIterator) {
			if (gpioPin.getPin().getAddress() == pi4jPin.getAddress()) {
				gpioProvisionedPin = gpioPin;
			}			
		}
		if (gpioProvisionedPin != null) {
			GpioFactory.getInstance().unprovisionPin(gpioProvisionedPin);
		}
	}
	
	protected Pin getPi4jPin(PinId pinId) {
		return PinIdToPi4jPinMap.getPi4jPin(pinId);
	}
	
	
}
