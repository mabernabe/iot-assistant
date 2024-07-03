package com.iotassistant.models.transductormqttinterface;

import java.util.List;

public interface MqttInterface {
	
	public static final String ARRIVE_LWT_TOPIC = "lwt";
	
	public List<String> getSubscribedTopics();

}
