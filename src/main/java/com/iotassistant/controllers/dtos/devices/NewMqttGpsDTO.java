package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.models.devices.Gps;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.mqtt.GpsMqttInterface;

public class NewMqttGpsDTO extends NewDeviceRequestDTO{

	public NewMqttGpsDTO() {
		super();
	}

	public Gps getGps() {
		GpsMqttInterface gpsMqttInterface = new GpsMqttInterface(super.getName());
		return new Gps(super.getName(), super.getDescription(), gpsMqttInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));	

	}

}
