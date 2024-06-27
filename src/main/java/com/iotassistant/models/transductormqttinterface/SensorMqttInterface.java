package com.iotassistant.models.transductormqttinterface;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.iotassistant.models.transductor.SensorInterface;
import com.iotassistant.models.transductor.SensorInterfaceVisitor;

@Entity
@DiscriminatorValue("sensorMQTTInterface")
public class SensorMqttInterface extends SensorInterface implements MqttInterface{
	
	@OneToOne(cascade=CascadeType.ALL)
	private MqttSensorTopic topic;
	
	
	
	public SensorMqttInterface() {
		super();
	}

	public SensorMqttInterface(String topic) {
		this();
		this.topic = new MqttSensorTopic(topic);	
	}

	public MqttSensorTopic getTopic() {
		return topic;
	}

	public void setTopic(MqttSensorTopic topic) {
		this.topic = topic;
	}

	@Override
	public List<String> getSubscribedTopics() {
		return this.getTopic().getSubscribedTopics();
	}

	@Override
	public void accept(SensorInterfaceVisitor sensorInterfaceVisitor, boolean setUp) {
		sensorInterfaceVisitor.visit(this, setUp);	
	}
	
}
