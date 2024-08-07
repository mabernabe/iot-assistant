package com.iotassistant.mqtt;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotassistant.models.devices.Device;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.services.DevicesFacadeService;



@Controller
public class MqttDevicesController implements MqttCallbackExtended{
	
	private volatile IMqttClient mqttclient;
	
	private static MqttDevicesController instance;
	
	@Autowired
	private DevicesFacadeService devicesService;
	
	Logger logger = LoggerFactory.getLogger(MqttDevicesController.class);
	

	public MqttDevicesController(@Value("${mqtt.broker.url}") String brokerURL, 
			@Value("${mqtt.folderpersistence}") String folderPersistence, 
			@Value("${mqtt.clientid}") String clientId,
			@Value("${mqtt.username:#{null}}") String username, 
			@Value("${mqtt.password:#{null}}") String password) {
		
		super();	
		try {
			mqttclient = new MqttClient(brokerURL, clientId, new MqttDefaultFilePersistence(folderPersistence));
			mqttclient.setCallback(this);
			mqttclient.connect(this.getMqttConnectOptions(username, password));
		} catch (MqttException e) { 
			logger.error(e.getLocalizedMessage());
		} 	
	}
	

	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	
	
	public static MqttDevicesController getInstance() {
		return instance;
	}

	private MqttConnectOptions getMqttConnectOptions(String username, String password) {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(false);
		options.setConnectionTimeout(0);
		if (username!= null && password != null) {
			options.setUserName(username);
			options.setPassword(password.toCharArray());
		}		
		return options;
	}

	
	public void subscribe(MqttInterface mqttinterface) throws MqttSecurityException, MqttException {
		for (String topic : mqttinterface.getSubscribedTopics()) {
			this.mqttclient.subscribe(topic);
		}
	}
	
	public void unsubscribe(MqttInterface mqttinterface) throws MqttSecurityException, MqttException {
		for (String topic : mqttinterface.getSubscribedTopics()) {
			this.mqttclient.unsubscribe(topic);
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message)  {
		Device device = devicesService.getDeviceByName(topic);
		if (device != null) {
			new MqttArrivedMessageService(device, message, devicesService).updateDevice();
		}			
	}
	
	public void setActuatorValue(MqttActuatorInterface actuatorMqttInterface, PropertyActuatedEnum propertyActuated, String value) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			MqttSetActuatorValueDTO mqttSetActuatorValueDTO = new MqttSetActuatorValueDTO(propertyActuated, value);
			byte[] jsonBytes = objectMapper.writeValueAsString(mqttSetActuatorValueDTO).getBytes();
			this.mqttclient.publish(actuatorMqttInterface.getSetValueTopic(), new MqttMessage(jsonBytes));
		} catch (JsonProcessingException | MqttException e) {
			logger.error(e.getLocalizedMessage());
		} 
		
	}
	

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		logger.info("Connection to MQTT broker completed: " + serverURI);
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		logger.info("Connection to MQTT broker lost: " + cause.getLocalizedMessage());
	}

	public boolean isConnected() {
		return mqttclient.isConnected();
	}
	
	@Override
	public void finalize() {
		try {
			logger.info("Disconnecting from MQTT broker");
			mqttclient.disconnect();
		} catch (MqttException e) {
			logger.error(e.getLocalizedMessage());
		}
	}

}
