package com.iotassistant.models.pi4jinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class PinIdToPi4jPinMap {
	
	@SuppressWarnings("serial")
	private static final HashMap<PinId, Entry<Pin, PinCapability>> PINID_TO_PI4JPIN = new HashMap<PinId, Entry<Pin, PinCapability>>(){{
		put(PinId.PIN_07, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_07, PinCapability.DIGITAL));
		put(PinId.PIN_08, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_08, PinCapability.DIGITAL));
		put(PinId.PIN_10, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_10, PinCapability.DIGITAL));
		put(PinId.PIN_11, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_11, PinCapability.DIGITAL));
		put(PinId.PIN_13, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_13, PinCapability.DIGITAL));
		put(PinId.PIN_15, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_15, PinCapability.DIGITAL));
		put(PinId.PIN_16, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_16, PinCapability.DIGITAL));
		put(PinId.PIN_18, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_18, PinCapability.DIGITAL));
		put(PinId.PIN_22, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_22, PinCapability.DIGITAL));
		put(PinId.PIN_29, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_29, PinCapability.DIGITAL));
		put(PinId.PIN_31, new SimpleEntry<Pin, PinCapability>(RaspiPin.GPIO_31, PinCapability.DIGITAL));
	}};
	

	protected static Pin getPi4jPin(PinId gpioPin) {
		Entry<Pin,PinCapability> pi4jPin = PINID_TO_PI4JPIN.get(gpioPin);
		return pi4jPin.getKey();
	}
	

	public static List<PinId> getPinIds(PinCapability pinCapability) {
		List<PinId> pins = new ArrayList<PinId>();
		for(PinId pinId : PINID_TO_PI4JPIN.keySet()) {
			if (PINID_TO_PI4JPIN.get(pinId).getValue().equals(pinCapability)) {
				pins.add(pinId);
			}
		}
		return pins;
	}
	

}
