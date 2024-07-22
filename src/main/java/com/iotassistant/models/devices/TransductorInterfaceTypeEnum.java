package com.iotassistant.models.devices;

import java.util.ArrayList;
import java.util.List;

public enum TransductorInterfaceTypeEnum {
	MQTT("MQTT");

	private String string;



	private TransductorInterfaceTypeEnum(String string) {
		this.string = string;
	}


	@Override
	public String toString() {
		return string;
	}


	public static List<String> getAllInstances() {
		List<String> instances = new ArrayList<String>();
		for (TransductorInterfaceTypeEnum instance : TransductorInterfaceTypeEnum.values()) {
			instances.add(instance.toString());
		}
		return instances;
	}



}
