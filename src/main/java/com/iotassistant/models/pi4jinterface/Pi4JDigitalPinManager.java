package com.iotassistant.models.pi4jinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.pininterface.DigitalPinListener;
import com.iotassistant.models.pininterface.PinId;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;

public class Pi4JDigitalPinManager extends Pi4JPinManager{
	
	private HashMap<PinId, GpioPinDigitalInput> inputPins = new HashMap<PinId, GpioPinDigitalInput >();
	
	private HashMap<PinId, GpioPinDigitalOutput> outputPins = new HashMap<PinId, GpioPinDigitalOutput >();
	
	private HashMap<DigitalPinListener,  Pi4JDigitalPinListener> listeners = new HashMap<>();


	private final static int DEFAULT_LISTENER_DEBOUNCE_TIME = 1000;


	public void setUpInputPin(PinId pinId) {
		Pin pi4jpin = getPi4jPin(pinId);
		unprovisionPi4jPin(pi4jpin);
		GpioPinDigitalInput digitalInputPin = GpioFactory.getInstance().provisionDigitalInputPin(pi4jpin);
		digitalInputPin.setDebounce(DEFAULT_LISTENER_DEBOUNCE_TIME);
		inputPins.put(pinId, digitalInputPin);
	}
	


	public boolean isInputPinHigh(PinId digitalPin) {
		return inputPins.get(digitalPin).isHigh();
	}
	

	public void addListener(DigitalPinListener digitalPinListener) {
		GpioPinDigitalInput pin = inputPins.get(digitalPinListener.getPinId());
		Pi4JDigitalPinListener pi4JPinlistener = new Pi4JDigitalPinListener(digitalPinListener);
		pin.addListener(new Pi4JDigitalPinListener(digitalPinListener));
		listeners.put(digitalPinListener, pi4JPinlistener);
		

	}
	
	
	public void removeListener(DigitalPinListener digitalPinListener) {
		GpioPinDigitalInput pin = inputPins.get(digitalPinListener.getPinId());
		Pi4JDigitalPinListener pi4JPinlistener = listeners.get(digitalPinListener);
		pin.removeListener(pi4JPinlistener);
		listeners.remove(digitalPinListener);

	}


	public void setOutputPinState(PinId pinId, boolean state) {
		GpioFactory.getInstance().setState(state, outputPins.get(pinId));
		
	}

	public void setUpOutputPin(PinId pinId) {
		Pin pi4jpin = getPi4jPin(pinId);
		unprovisionPi4jPin(pi4jpin);
		GpioPinDigitalOutput digitalOutputPin = GpioFactory.getInstance().provisionDigitalOutputPin(pi4jpin);
		outputPins.put(pinId, digitalOutputPin);
	}

	public boolean getOutputPinState(PinId pinId) {
		return GpioFactory.getInstance().getState(outputPins.get(pinId)).isHigh();
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
