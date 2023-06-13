package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Mqtt_Actuator_Topic")
public class MqttActuatorTopic extends MqttTransductorTopic{
	
	private static final String ARRIVE_STATE_TOPIC = "/value";
	
	private static final String SET_STATE_TOPIC = "/setvalue";
	
	public MqttActuatorTopic() {
		super();
	}
		
	public MqttActuatorTopic(String baseTopic) {
		super(baseTopic);
	}


	@Override
	protected List<String> _getSubscribedTopics() {
		List<String> subscriberTopics = new ArrayList<String>();
		subscriberTopics.add(getStateTopic());
		return subscriberTopics;
	}

	public String getStateTopic() {
		return baseTopic.concat(ARRIVE_STATE_TOPIC);
	}
	
	public String getSentStateTopic() {
		return baseTopic.concat(SET_STATE_TOPIC);
	}

}
