package com.iotassistant.models.transductorpininterface;

import com.iotassistant.models.pininterface.DigitalPinListener;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.transductor.SensorMeasureValueEvent;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalPropertyMeasured;

public class SensorMeasureDigitalPinListener implements DigitalPinListener{
	
	SensorMeasureObserver observer;

	boolean valueThresold;
	
	PinId pinId;
	

	public SensorMeasureDigitalPinListener(SensorMeasureObserver observer, boolean valueThresold, PinId pinId) {
		this.valueThresold = valueThresold;
		this.observer = observer;
		this.pinId = pinId;
	}
	
	@Override
	public void notify(boolean digitalValue) {
		if (digitalValue == valueThresold) {
			String value = DigitalPropertyMeasured.getDigitalValueString(digitalValue);
			SensorMeasureValueEvent digitalStateChangeInterfaceEvent = new SensorMeasureValueEvent(value);
			this.observer.notify(digitalStateChangeInterfaceEvent);
		}
	}

	@Override
	public PinId getPinId() {
		return pinId;
	}

}
