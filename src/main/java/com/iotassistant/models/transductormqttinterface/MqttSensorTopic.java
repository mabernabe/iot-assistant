package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MqttSensorTopic")
public class MqttSensorTopic extends MqttTransductorTopic {
	
	private static final String ARRIVE_MEASURE_TOPIC = "/measure";
	
	public MqttSensorTopic() {
		super();
	}


	public MqttSensorTopic(String baseTopic) {
		super(baseTopic);
	}
	
	
	public String getMeasureTopic() {
		return baseTopic.concat(ARRIVE_MEASURE_TOPIC);
	}


	@Override
	protected List<String> _getSubscribedTopics() {
		List<String> subscriberTopics = new ArrayList<String>();
		subscriberTopics.add(getMeasureTopic());
		return subscriberTopics;
	}



}
