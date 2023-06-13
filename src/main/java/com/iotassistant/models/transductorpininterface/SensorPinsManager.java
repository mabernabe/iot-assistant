package com.iotassistant.models.transductorpininterface;

import java.util.List;

import com.iotassistant.models.PlatformInterfacesFactory;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.pininterface.PlatformPinInterface;
import com.iotassistant.models.transductor.SensorMeasure;

public abstract class SensorPinsManager {
		
	protected PlatformPinInterface platformPinInterface;
		
	protected abstract List<SensorMeasure> getMeasures() throws PinInterfaceException;
	
	protected abstract void setDownPins() throws PinInterfaceException;

	public SensorPinsManager() throws PinInterfaceException {
		super();
		this.platformPinInterface = PlatformInterfacesFactory.getInstance().getPlatformPinInterface();
	}
	
}
