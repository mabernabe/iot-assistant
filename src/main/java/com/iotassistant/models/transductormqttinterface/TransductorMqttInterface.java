package com.iotassistant.models.transductormqttinterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotassistant.models.PlatformInterfacesFactory;
import com.iotassistant.models.mqttinterface.MqttPlatformInterface;
import com.iotassistant.models.mqttinterface.MqttPlatformInterfaceClient;
import com.iotassistant.models.transductor.TransductorInterfaceException;

public class TransductorMqttInterface implements MqttPlatformInterfaceClient{
	
	private MqttTransductorTopic mqttTransductorTopic;	

	private MqttPlatformInterface mqttPlatformInterface;

	private boolean interfaceError = true;
	
	TransductorMqttInterfaceObserver observer;
	
	public TransductorMqttInterface(TransductorMqttInterfaceObserver observer) {
		super();
		this.observer = observer;
		mqttPlatformInterface = PlatformInterfacesFactory.getInstance().getPlatformMqttInterface();
	}
	
	public void setUp(MqttTransductorTopic mqttTransductorTopic) throws TransductorInterfaceException {
		try {
			this.mqttTransductorTopic = mqttTransductorTopic;
			this.registerSubscriber();
			this.printError("Insterface error set to false in setup");
			interfaceError = false;
		} catch (TransductorInterfaceException e) {
			this.printError("Insterface error set to true in setup");
			interfaceError = true;
			throw e;
		}
	}
	
	private void printError(String string) {
		System.out.print("Transductor name: " + mqttTransductorTopic.getBaseTopic() + ": ");
		System.out.println(string);
		
	}

	public void setDown() throws TransductorInterfaceException {
		this.unRegisterSubscriber();
	}
		
	private void registerSubscriber() throws TransductorInterfaceException {
		unRegisterSubscriber();
		try {
			this.mqttPlatformInterface.registerSubscriber(this, this.mqttTransductorTopic.getSubscribedTopics());
		} catch (Exception e) {
			this.printError("Error registering subscribers ");
			throw new TransductorInterfaceException(e.getMessage());
		}
	}
	
	private void unRegisterSubscriber() throws TransductorInterfaceException  {
		try {
			this.mqttPlatformInterface.unRegisterSubscriber(this, this.mqttTransductorTopic.getSubscribedTopics());
		} catch (Exception e) {
			throw new TransductorInterfaceException(e.getMessage());
		}
	}

	@Override
	public void messageArrived(String topic, String message) {
		if (this.mqttTransductorTopic._getSubscribedTopics().contains(topic)) {
			if (mqttTransductorTopic.isLWTTopic(topic)) {
				observer.lwtMessageArrived(message);
			}
			else {
				interfaceError = observer.messageArrived(topic, message);	
			}
		}
		
	}

	public void connectionLost() {
		interfaceError = true;	
	}

	@Override
	public void connectComplete() {
		try {
			System.out.println("Connection complete in transductor mqtt interface");
			this.registerSubscriber();
			interfaceError = false;
			this.printError("Interface error set to false in in connection complete");
		} catch (TransductorInterfaceException e) {
			this.printError("Interface error set to true in connection complete");
			interfaceError = true;
		}	
	}

	public boolean isInterfaceError() {
		if (interfaceError) {
			this.printError("Interface error is true");
		}
		return interfaceError;
	}

	public void sendMessage(String topic, Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] jsonBytes = objectMapper.writeValueAsString(object).getBytes(); 
		this.mqttPlatformInterface.publish(topic, jsonBytes);
		
	}
	

}
