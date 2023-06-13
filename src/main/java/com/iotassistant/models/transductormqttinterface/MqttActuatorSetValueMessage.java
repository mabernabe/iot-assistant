package com.iotassistant.models.transductormqttinterface;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

public class MqttActuatorSetValueMessage {
	
	private PropertyActuatedEnum propertyActuated;
	
	private String value;

	public MqttActuatorSetValueMessage(PropertyActuatedEnum propertyActuated, String value) {
		super();
		this.propertyActuated = propertyActuated;
		this.value = value;
	}

	public String getPropertyActuated() {
		return propertyActuated.toStringWithUnit();
	}

	public void setPropertyActuated(PropertyActuatedEnum propertyActuated) {
		this.propertyActuated = propertyActuated;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}
