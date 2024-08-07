package com.iotassistant.controllers.dtos.devices;

import com.iotassistant.models.devices.Gps;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.mqtt.MqttGpsInterface;

public class NewMqttGpsDTO extends NewDeviceRequestDTO{

	public NewMqttGpsDTO() {
		super();
	}

	public Gps getGps() {
		MqttGpsInterface gpsMqttInterface = new MqttGpsInterface(super.getName());
		return new Gps(super.getName(), super.getDescription(), gpsMqttInterface, WatchdogInterval.getInstance(super.getWatchdogInterval()));	

	}

}
