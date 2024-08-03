package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.models.devices.transductors.SensorInterface;

@Entity
@DiscriminatorValue("sensorMQTTInterface")
public class SensorMqttInterface extends SensorInterface implements MqttInterface{
	
	private String topic;
	
	public SensorMqttInterface() {
		super();
	}

	public SensorMqttInterface(String topic) {
		this();
		this.topic = topic;	
	}


	@Override
	public List<String> getSubscribedTopics() {
		List<String> subscribedTopics = new ArrayList<String>();
		subscribedTopics.add(topic);
		subscribedTopics.add(topic + "/" + ARRIVE_LWT_TOPIC);
		return subscribedTopics;
	}

	@Override
	public void accept(DeviceInterfaceVisitor deviceInterfaceVisitor){
		deviceInterfaceVisitor.visit(this);	
	}

	@Override
	public String getTopic() {
		return this.topic;
	}



	
}
