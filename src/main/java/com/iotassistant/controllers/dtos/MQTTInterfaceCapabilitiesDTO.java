package com.iotassistant.controllers.dtos;

import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;

public class MQTTInterfaceCapabilitiesDTO extends TransductorInterfaceCapabilitiesDTO{
	
	String broker;

	public MQTTInterfaceCapabilitiesDTO(boolean isAvailable, String broker) {
		super(TransductorInterfaceTypeEnum.MQTT.toString(), isAvailable);
		this.broker = broker;
	}

	public String getBroker() {
		return broker;
	}

	
}
