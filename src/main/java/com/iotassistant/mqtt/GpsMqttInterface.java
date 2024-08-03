package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.models.devices.GpsInterface;

@Entity
@DiscriminatorValue("gpsMQTTInterface")
public class GpsMqttInterface extends GpsInterface implements MqttInterface{

private String topic;
	
	public GpsMqttInterface() {
		super();
	}

	public GpsMqttInterface(String topic) {
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
	public void accept(DeviceInterfaceVisitor transductorInterfaceVisitor){
		transductorInterfaceVisitor.visit(this);	
	}

	@Override
	public String getTopic() {
		return this.topic;
	}

}
