package com.iotassistant.models.transductormqttinterface;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.iotassistant.models.transductor.ActuatorInterface;

@Entity
@DiscriminatorValue("actuatorMQTTInterface")
public class ActuatorMqttInterface extends ActuatorInterface{
	
	@OneToOne(cascade=CascadeType.ALL)
	private MqttActuatorTopic topic;

	public ActuatorMqttInterface() {
		super();
		
	}

	public ActuatorMqttInterface(String topic) {
		this();
		this.topic = new MqttActuatorTopic(topic);	
	}



	

}
