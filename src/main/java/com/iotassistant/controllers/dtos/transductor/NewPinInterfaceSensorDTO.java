package com.iotassistant.controllers.dtos.transductor;

import java.util.HashMap;

import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductorpininterface.SensorPinInterface;

public class NewPinInterfaceSensorDTO extends NewTransductorRequestDTO{
	
	private HashMap<PropertyMeasuredEnum, PinId  > pinsConfiguration;

	public void setPinsConfiguration(HashMap<PropertyMeasuredEnum, PinId> pinsConfiguration) {
		this.pinsConfiguration = pinsConfiguration;
	}

	
	public Sensor getSensor() {
		SensorPinInterface sensorPinInterface = new SensorPinInterface(pinsConfiguration);
		return new Sensor(super.getName(), super.getDescription(), sensorPinInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));	
	}


}
