package com.iotassistant.controllers;

import java.io.IOException;
import java.util.List;

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

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.models.transductormqttinterface.MqttInterface;
import com.iotassistant.services.SensorsService;
import com.iotassistant.utils.JSONParser;



@Controller
public class MQTTTransductorsController implements MqttCallbackExtended{
	
	private volatile IMqttClient mqttclient;

	@Autowired
	private SensorsService sensorsService;
	

	public MQTTTransductorsController(@Value("${mqtt.broker.url}") String brokerURL, @Value("${mqtt.folderpersistence}") String folderPersistence) {
		super();	
		try {
			mqttclient = new MqttClient(brokerURL, MqttClient.generateClientId(), new MqttDefaultFilePersistence(folderPersistence));
			mqttclient.setCallback(this);
			mqttclient.connect(this.getMqttConnectOptions());
		} catch (MqttException e) { 
			e.printStackTrace();
		} 	
	}
	
	private MqttConnectOptions getMqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
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
		try {
			MQTTSensorValuesDTO sensorValuesDTO = new JSONParser().parseJsonBodyAs(MQTTSensorValuesDTO.class, message.toString());
			List<PropertyMeasuredEnum> sensorProperties = sensorsService.getSensorByName(topic).getPropertiesMeasured();
			if (!sensorValuesDTO.hasErrors(sensorProperties)) {
				sensorsService.update(topic, sensorValuesDTO.getValues());
			}
		} catch (IOException e) {
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
