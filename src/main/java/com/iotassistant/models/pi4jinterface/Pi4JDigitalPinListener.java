package com.iotassistant.models.pi4jinterface;

import com.iotassistant.models.pininterface.DigitalPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Pi4JDigitalPinListener implements GpioPinListenerDigital{

	DigitalPinListener digitalPinListener;
	
	public Pi4JDigitalPinListener(DigitalPinListener digitalPinListener) {
		this.digitalPinListener = digitalPinListener; 
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		digitalPinListener.notify(event.getState().isHigh());

	}

}
