package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

public interface MqttInterface {
	
	public static final String ARRIVE_LWT_TOPIC = "lwt";
	
	public default List<String> getSubscribedTopics() {
		List<String> subscribedTopics = new ArrayList<String>();
		subscribedTopics.add(this.getTopic());
		subscribedTopics.add(this.getTopic() + "/" + ARRIVE_LWT_TOPIC);
		subscribedTopics.addAll(this._getSubscribedTopic());
		return subscribedTopics;
	}
	
	public List<String> _getSubscribedTopic();

	public String getTopic();

}
