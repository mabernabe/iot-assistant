package com.iotassistant.controllers.dtos.devices.transductors;


import java.util.List;

import com.iotassistant.controllers.dtos.devices.NewDeviceRequestDTO;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.models.devices.transductors.Sensor;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.mqtt.SensorMqttInterface;

public class NewMqttInterfaceSensorDTO extends NewDeviceRequestDTO{
	
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
