package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.transductor.ActuatorInterface;

@Entity
@DiscriminatorValue("actuatorMQTTInterface")
public class ActuatorMqttInterface extends ActuatorInterface implements MqttInterface{
	
	private String topic;

	public ActuatorMqttInterface() {
		super();
		
	}

	public ActuatorMqttInterface(String topic) {
		this();
		this.topic = topic;	
	}

	@Override
	public List<String> getSubscribedTopics() {
		List<String> subscribedTopics = new ArrayList<String>();
		subscribedTopics.add(topic);
		subscribedTopics.add(ARRIVE_LWT_TOPIC);
		return subscribedTopics;
	}



	

}
