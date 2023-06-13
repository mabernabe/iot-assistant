package com.iotassistant.models.transductor;

public enum TransductorInterfaceTypeEnum {
	PIN("PIN"),
	MQTT("MQTT");

	private String string;



	private TransductorInterfaceTypeEnum(String string) {
		this.string = string;
	}


	@Override
	public String toString() {
		return string;
	}



}
