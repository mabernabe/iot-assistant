package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.transductor.SensorInterface;
import com.iotassistant.models.transductor.SensorInterfaceVisitor;

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
	public void accept(SensorInterfaceVisitor sensorInterfaceVisitor, boolean setUp) {
		sensorInterfaceVisitor.visit(this, setUp);	
	}

	public String getTopic() {
		return this.topic;
	}
	
}
