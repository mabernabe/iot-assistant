package com.iotassistant.models.mqttinterface;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;


public class MqttPlatformInterface implements MqttCallbackExtended{

	private volatile IMqttClient iMqttclient;

	private ConcurrentHashMap<MqttPlatformInterfaceClient, List<String>> mqttSubscribers = new ConcurrentHashMap<>();


	public MqttPlatformInterface(String brokerURL, String folderPersistence) {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(0);	
		try {
			iMqttclient = new MqttClient(brokerURL, MqttClient.generateClientId(), new MqttDefaultFilePersistence(folderPersistence));
			iMqttclient.setCallback(this);
			iMqttclient.connect(options);
		} catch (MqttException e) { 
			e.printStackTrace();
		} 	
	}



	public void registerSubscriber(MqttPlatformInterfaceClient mqttPlatformInterfaceClient, List<String> subscriberTopics) throws Exception {
		mqttSubscribers.put(mqttPlatformInterfaceClient, subscriberTopics);
		for (String topic : subscriberTopics) {
			iMqttclient.subscribe(topic);
		}

	}


	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost ");
		for (MqttPlatformInterfaceClient mqttPlatformInterfaceClient: mqttSubscribers.keySet()) {
			mqttPlatformInterfaceClient.connectionLost();				
		}
	}


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Arrived message: " + "topic: " + topic + " message: " + message.toString());
		for (MqttPlatformInterfaceClient mqttPlatformInterfaceClient: mqttSubscribers.keySet()) {
			for (String subscribedTopic : mqttSubscribers.get(mqttPlatformInterfaceClient)) {
				if (subscribedTopic.equals(topic)) {
					mqttPlatformInterfaceClient.messageArrived(topic, message.toString());
				}
			}			
		}		
	}


	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}


	public void publish(String topic, byte[] bytes) throws Exception {
		iMqttclient.publish(topic, new MqttMessage(bytes));
	}



	public void unRegisterSubscriber(MqttPlatformInterfaceClient mqttPlatformInterfaceClient, List<String> subscriberTopics) throws Exception {
		mqttSubscribers.remove(mqttPlatformInterfaceClient, subscriberTopics);
		for (String topic : subscriberTopics) {
			iMqttclient.unsubscribe(topic);

		}

	}


	public boolean isAvailable() {
		return iMqttclient.isConnected();
	}



	@Override
	public void connectComplete(boolean reconnect, String serverURI) {;
		for (MqttPlatformInterfaceClient mqttPlatformInterfaceClient: mqttSubscribers.keySet()) {
			mqttPlatformInterfaceClient.connectComplete();			
		}
	}

}
