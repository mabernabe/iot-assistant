package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.transductor.SensorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;

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
	public void accept(TransductorInterfaceVisitor transductorInterfaceVisitor){
		transductorInterfaceVisitor.visit(this);	
	}

	@Override
	public String getTopic() {
		return this.topic;
	}



	
}
