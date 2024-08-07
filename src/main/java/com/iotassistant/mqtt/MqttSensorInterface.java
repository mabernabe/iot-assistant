package com.iotassistant.mqtt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.devices.DeviceInterfaceVisitor;
import com.iotassistant.models.devices.transductors.SensorInterface;

@Entity
@DiscriminatorValue("sensorMQTTInterface")
public class MqttSensorInterface extends SensorInterface implements MqttInterface{
	
	private String topic;
	
	public MqttSensorInterface() {
		super();
	}

	public MqttSensorInterface(String topic) {
		this();
		this.topic = topic;	
	}

	@Override
	public void accept(DeviceInterfaceVisitor deviceInterfaceVisitor){
		deviceInterfaceVisitor.visit(this);	
	}

	@Override
	public String getTopic() {
		return this.topic;
	}

	@Override
	public List<String> _getSubscribedTopic() {
		return new ArrayList<String>();
	}	
}
