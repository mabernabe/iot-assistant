package com.iotassistant.models.pi4jinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.pininterface.PinId;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.GpioPinAnalogOutput;
import com.pi4j.io.gpio.Pin;

public class Pi4JAnalogPinManager extends Pi4JPinManager{
	
	
	private HashMap<PinId, GpioPinAnalogInput> inputPins = new HashMap<PinId, GpioPinAnalogInput>();
	
	private HashMap<PinId, GpioPinAnalogOutput> outputPins = new HashMap<PinId, GpioPinAnalogOutput>();

	public String getAnalogValue(PinId pinId) {
		GpioPinAnalogInput pin = inputPins.get(pinId);
		return String.valueOf(pin.getValue());
	}

	
	public void setUpInputPin(PinId pinId) {
		Pin pi4jPin = getPi4jPin(pinId);
		unprovisionPi4jPin(pi4jPin);
		inputPins.put(pinId, GpioFactory.getInstance().provisionAnalogInputPin(pi4jPin));

	}


	public void setUpOutputPin(PinId pinId) {
		Pin pi4jPin = getPi4jPin(pinId);
		unprovisionPi4jPin(pi4jPin);
		outputPins.put(pinId, GpioFactory.getInstance().provisionAnalogOutputPin(pi4jPin));
		
	}


	public void setOutputPinValue(String value, PinId pinId) {
		GpioPinAnalogOutput pi4jPin = outputPins.get(pinId);
		pi4jPin.setValue(Double.valueOf(value));
		
	}


	public String getOutputPinValue(PinId pinId) {
		GpioPinAnalogOutput pi4jPin = outputPins.get(pinId);
		return String.valueOf(pi4jPin.getValue());
	}


	public List<PinId> getUsedPins() {
		List<PinId> usedPins = new ArrayList<PinId>(inputPins.keySet());
		usedPins.addAll(new ArrayList<PinId>(outputPins.keySet()));
		return usedPins;
	}


	public void setDownInputPin(PinId pinId) {
		Pin pi4jPin = getPi4jPin(pinId);
		unprovisionPi4jPin(pi4jPin);
		inputPins.remove(pinId);
	}

	
}
