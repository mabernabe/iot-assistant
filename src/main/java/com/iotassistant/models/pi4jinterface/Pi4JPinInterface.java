package com.iotassistant.models.pi4jinterface;


import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.pininterface.DigitalPinListener;
import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.pininterface.PlatformPinInterface;
import com.pi4j.io.gpio.GpioFactory;


public class Pi4JPinInterface implements PlatformPinInterface {


	private volatile Pi4JDigitalPinManager pi4jDigitalPinManager = new Pi4JDigitalPinManager();

	private volatile Pi4JAnalogPinManager pi4JAnalogPinManager = new Pi4JAnalogPinManager();

	@Override
	public String getAnalogInputValue(PinId pinId) throws PinInterfaceException {
		try
		{ 
			return pi4JAnalogPinManager.getAnalogValue(pinId);	
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void setUpAnalogInputPin(PinId pinId) throws PinInterfaceException{
		try
		{ 
			pi4JAnalogPinManager.setUpInputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void setUpAnalogOutputPin(PinId pinId) throws PinInterfaceException {
		try
		{
			pi4JAnalogPinManager.setUpOutputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void setAnalogOutputPinValue(String value, PinId pinId) throws PinInterfaceException {
		try
		{
			pi4JAnalogPinManager.setOutputPinValue(value, pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public String getAnalogOutputPinValue(PinId pinId) throws PinInterfaceException {
		try
		{
			return pi4JAnalogPinManager.getOutputPinValue(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void setUpDigitaInputPin(PinId pinId) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.setUpInputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public boolean isDigitalInputPinHigh(PinId digitalPin) throws PinInterfaceException {
		try
		{
			return pi4jDigitalPinManager.isInputPinHigh(digitalPin);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void addListener(DigitalPinListener digitalPinListener) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.addListener(digitalPinListener);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public void removeListener(DigitalPinListener digitalPinListener) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.removeListener(digitalPinListener);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}

	}


	public List<PinId> getAvailablePins(PinCapability pinCapability) {
		List<PinId> allPins = PinIdToPi4jPinMap.getPinIds(pinCapability);
		List<PinId> availablePins = new ArrayList<PinId>();
		for (PinId pin : allPins) {
			if (pinCapability.isDigital() && !pi4jDigitalPinManager.getUsedPins().contains(pin)) {
				availablePins.add(pin);
			}
			if (!pinCapability.isDigital() && !pi4JAnalogPinManager.getUsedPins().contains(pin)) {
				availablePins.add(pin);
			}
		}
		return availablePins;
	}


	@Override
	public String getPlatformName() {
		return "RaspberryPi";
	}


	@Override
	public void setUpDigitalOutputPin(PinId pinId) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.setUpOutputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}

	}

	@Override
	public void setDigitalOutputPinState(boolean state, PinId pinId) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.setOutputPinState(pinId, state);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}

	}


	@Override
	public boolean getDigitalOutputPinState(PinId pinId) throws PinInterfaceException {
		try
		{
			return pi4jDigitalPinManager.getOutputPinState(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

	@Override
	public boolean isAvailable() {
		try
		{
			GpioFactory.getInstance().getProvisionedPins();
			return true;
		}catch(RuntimeException|Error exception) {
			return false;
		}
	}

	@Override
	public void setDownAnalogInputPin(PinId pinId) throws PinInterfaceException {
		try
		{
			pi4JAnalogPinManager.setDownInputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}

	}

	@Override
	public void setDownDigitalInputPin(PinId pinId) throws PinInterfaceException {
		try
		{
			pi4jDigitalPinManager.setDownInputPin(pinId);
		}catch(RuntimeException|Error exception) {
			throw new PinInterfaceException(exception.getMessage());
		}
	}

}
