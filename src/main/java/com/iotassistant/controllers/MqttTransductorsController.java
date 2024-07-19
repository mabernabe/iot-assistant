package com.iotassistant.controllers;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.MqttInterface;
import com.iotassistant.services.TransductorsService;



@Controller
public class MqttTransductorsController implements MqttCallbackExtended{
	
	private volatile IMqttClient mqttclient;
	
	private static MqttTransductorsController instance;
	
	@Autowired
	private TransductorsService transductorsService;
	

	public MqttTransductorsController(@Value("${mqtt.broker.url}") String brokerURL, 
			@Value("${mqtt.folderpersistence}") String folderPersistence, 
			@Value("${mqtt.clientid}") String clientId) {
		super();	
		try {
			mqttclient = new MqttClient(brokerURL, clientId, new MqttDefaultFilePersistence(folderPersistence));
			mqttclient.setCallback(this);
			mqttclient.connect(this.getMqttConnectOptions());
		} catch (MqttException e) { 
			e.printStackTrace();
		} 	
	}
	

	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	
	
	public static MqttTransductorsController getInstance() {
		return instance;
	}

	private MqttConnectOptions getMqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(false);
		options.setConnectionTimeout(0);
		return options;
	}

	
	public void subscribe(MqttInterface mqttinterface) throws MqttSecurityException, MqttException {
		for (String topic : mqttinterface.getSubscribedTopics()) {
			this.mqttclient.subscribe(topic);
		}
	}
	
	public void unsubscribe(MqttInterface mqttinterface) throws MqttSecurityException, MqttException {
		for (String topic : mqttinterface.getSubscribedTopics()) {
			this.mqttclient.subscribe(topic);
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)  {
		Transductor transductor = transductorsService.getTransductorByName(topic);
		if (transductor != null) {
			new TransductorMqttMessageService(transductor, message, transductorsService).updateTransductor();
		}			
	}
	
	public void setActuatorValue(ActuatorMqttInterface actuatorMqttInterface, PropertyActuatedEnum propertyActuated, String value) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			MqttSetActuatorValueDTO mqttSetActuatorValueDTO = new MqttSetActuatorValueDTO(propertyActuated, value);
			byte[] jsonBytes = objectMapper.writeValueAsString(mqttSetActuatorValueDTO).getBytes();
			this.mqttclient.publish(actuatorMqttInterface.getSetValueTopic(), new MqttMessage(jsonBytes));
		} catch (JsonProcessingException | MqttException e) {
			e.printStackTrace();
		} 
		
	}
	

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		
	}
	
	@Override
	public void connectionLost(Throwable cause) {
	}

	public boolean isConnected() {
		return mqttclient.isConnected();
	}

}
