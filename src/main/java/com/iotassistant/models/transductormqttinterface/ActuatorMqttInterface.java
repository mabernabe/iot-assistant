package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.transductor.ActuatorInterface;
import com.iotassistant.models.transductor.ActuatorInterfaceVisitor;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;

@Entity
@DiscriminatorValue("actuatorMQTTInterface")
public class ActuatorMqttInterface extends ActuatorInterface implements MqttInterface{
	
	private String topic;
	
	private static String SET_VALUE_TOPIC = "/value";

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
		subscribedTopics.add(topic + "/" + ARRIVE_LWT_TOPIC);
		return subscribedTopics;
	}

	@Override
	public void accept(TransductorInterfaceVisitor transductorInterfaceVisitor) {
		transductorInterfaceVisitor.visit(this);
		
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
	

}
