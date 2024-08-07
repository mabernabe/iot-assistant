package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.models.devices.transductors.ActuatorInterface;
import com.iotassistant.models.devices.transductors.ActuatorInterfaceVisitor;

@Entity
@DiscriminatorValue("actuatorMQTTInterface")
public class MqttActuatorInterface extends ActuatorInterface implements MqttInterface{
	
	private String topic;
	
	private static String SET_VALUE_TOPIC = "/value";

	public MqttActuatorInterface() {
		super();		
	}

	public MqttActuatorInterface(String topic) {
		this();
		this.topic = topic;	
	}

	@Override
	public void accept(DeviceInterfaceVisitor deviceInterfaceVisitor) {
		deviceInterfaceVisitor.visit(this);
		
	}


	public String getTopic() {
		return this.topic;
	}

	@Override
	public void accept(ActuatorInterfaceVisitor actuatorInterfaceVisitor) {
		actuatorInterfaceVisitor.visit(this);		
	}
	
	public String getSetValueTopic() {
		return this.topic.concat(SET_VALUE_TOPIC);
	}

	@Override
	public List<String> _getSubscribedTopic() {
		return new ArrayList<String>();
	}
	

}
