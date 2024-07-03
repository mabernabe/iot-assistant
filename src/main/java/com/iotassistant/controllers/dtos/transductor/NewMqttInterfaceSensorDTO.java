package com.iotassistant.controllers.dtos.transductor;


import java.util.List;

import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.WatchdogInterval;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

public class NewMqttInterfaceSensorDTO extends NewTransductorRequestDTO{
	
	private List<PropertyMeasuredEnum> propertiesMeasured;

	public List<PropertyMeasuredEnum> getPropertiesMeasured() {
		return propertiesMeasured;
	}
	

	public void setPropertiesMeasured(List<PropertyMeasuredEnum> propertiesMeasured) {
		this.propertiesMeasured = propertiesMeasured;
	}

	public Sensor getSensor() {
		SensorMqttInterface sensorMqttInterface = new SensorMqttInterface(super.getName());
		return new Sensor(super.getName(), super.getDescription(), propertiesMeasured, sensorMqttInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));	
	}
	
	

}
