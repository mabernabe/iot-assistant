package com.iotassistant.models.pininterface;

import java.util.List;

public interface PlatformPinInterface {

	void setUpAnalogInputPin(PinId pinId) throws PinInterfaceException;
	
	String getAnalogInputValue(PinId pinId) throws PinInterfaceException;
	
	void setUpDigitaInputPin(PinId pinId) throws PinInterfaceException;
	
	boolean isDigitalInputPinHigh(PinId pinId) throws PinInterfaceException;
	
	void addListener(DigitalPinListener digitalPinListener) throws PinInterfaceException;
	
	void removeListener(DigitalPinListener digitalPinListener) throws PinInterfaceException;

	List<PinId> getAvailablePins(PinCapability pinCapability);
	
	String getPlatformName();
	
	void setUpDigitalOutputPin(PinId pinId) throws PinInterfaceException;

	void setDigitalOutputPinState(boolean state, PinId pinId) throws PinInterfaceException;

	boolean getDigitalOutputPinState(PinId pinId) throws PinInterfaceException;

	void setUpAnalogOutputPin(PinId pinId) throws PinInterfaceException;

	void setAnalogOutputPinValue(String value, PinId pinId) throws PinInterfaceException;

	String getAnalogOutputPinValue(PinId pinId) throws PinInterfaceException;

	boolean isAvailable();

	void setDownAnalogInputPin(PinId pinId) throws PinInterfaceException;

	void setDownDigitalInputPin(PinId pinId) throws PinInterfaceException;

}
