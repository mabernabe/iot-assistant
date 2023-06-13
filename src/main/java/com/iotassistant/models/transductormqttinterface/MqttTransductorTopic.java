package com.iotassistant.models.transductormqttinterface;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="transductor_type")
@Table(name="transductor_topic")
public abstract class MqttTransductorTopic {
	
	@Transient
	private static final String ARRIVE_LWT_TOPIC = "/lwt";
	
	@Id
	protected String baseTopic;

	public MqttTransductorTopic() {
		super();
	}

	public MqttTransductorTopic(String baseTopic) {
		super();
		this.baseTopic = baseTopic;
	}
	
	public List<String> getSubscribedTopics() {
		List<String> subscribedTopics = new ArrayList<String>();
		subscribedTopics.addAll(_getSubscribedTopics());
		subscribedTopics.add(ARRIVE_LWT_TOPIC);
		return subscribedTopics;
	}

	protected abstract List<String> _getSubscribedTopics();

	public boolean isLWTTopic(String topic) {
		return topic.equals(ARRIVE_LWT_TOPIC);
	}

	public String getBaseTopic() {
		return baseTopic;
	}
	
	

}
