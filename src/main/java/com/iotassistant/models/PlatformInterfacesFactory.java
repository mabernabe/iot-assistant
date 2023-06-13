package com.iotassistant.models;


import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.mqttinterface.MqttPlatformInterface;
import com.iotassistant.models.pi4jinterface.Pi4JPinInterface;
import com.iotassistant.models.pininterface.PlatformPinInterface;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;
import com.iotassistant.utils.ApplicationProperties;

public class PlatformInterfacesFactory {
	
	private static volatile PlatformInterfacesFactory INSTANCE = null;

	private static final String PROPERTIES_MQTT_INTERFACE_BROKER_KEY = "mqtt.broker.url";
	
	private static final String DEFAULT_MQTT_INTERFACE_BROKER = "tcp://broker.hivemq.com:1883";
	
	private static final String PROPERTIES_MQTT_FOLDER_PERSISTENCE_KEY = "mqtt.filepersistence";
	
	private static final String DEFAULT_MQTT_FOLDER_PERSISTENCE = "/tmp";
	
	private static final String PROPERTIES_PLATFORM_KEY = "platform";
	
	private static final String RASPBERRY_PI_PLATFORM = "raspberrypi";	
	
	private MqttPlatformInterface platformMqttInterface = null;

	private PlatformPinInterface platformPinInterface = null;
	
	private HttpPlatformInterface platformHttpInterface = null;
	
	private ApplicationProperties applicationProperties = ApplicationProperties.getInstance();
	
	private PlatformInterfacesFactory() {
		super();		
	}
	
	
	public static PlatformInterfacesFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PlatformInterfacesFactory();
		}
		return INSTANCE;
	}


	public MqttPlatformInterface getPlatformMqttInterface() {
		if (platformMqttInterface == null) {
			try {
				String brokerURL = applicationProperties.getProperty(PROPERTIES_MQTT_INTERFACE_BROKER_KEY, DEFAULT_MQTT_INTERFACE_BROKER);
				String folderPersistence = applicationProperties.getProperty(PROPERTIES_MQTT_FOLDER_PERSISTENCE_KEY, DEFAULT_MQTT_FOLDER_PERSISTENCE);
				platformMqttInterface = new MqttPlatformInterface(brokerURL, folderPersistence);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return platformMqttInterface;
	}


	public PlatformPinInterface getPlatformPinInterface()  {
		if (platformPinInterface == null) {
			String platform = applicationProperties.getProperty(PROPERTIES_PLATFORM_KEY);
			if (platform.equals(RASPBERRY_PI_PLATFORM)) {
				platformPinInterface = new Pi4JPinInterface();		
			}
		}
		return platformPinInterface;
	}
	
	public HttpPlatformInterface getHttpPlatformInterface() {
		if (platformHttpInterface == null) {
			platformHttpInterface = new HttpPlatformInterface();
		}
		return platformHttpInterface;
		
	}

	public List<String >getAvailableTransductorInterfaces() {
		List<String> availableInterfaces = new ArrayList<String>();
		PlatformPinInterface platformPinInterface = this.getPlatformPinInterface();
		if (platformPinInterface != null && platformPinInterface.isAvailable()) {
			availableInterfaces.add(TransductorInterfaceTypeEnum.PIN.toString());		
		}
		MqttPlatformInterface platformMQTTInterface = this.getPlatformMqttInterface();
		if (platformMQTTInterface != null && platformMQTTInterface.isAvailable()) {
			availableInterfaces.add(TransductorInterfaceTypeEnum.MQTT.toString());
		}
		return availableInterfaces;
	}


	public String getPlatformName() {
		return applicationProperties.getProperty(PROPERTIES_PLATFORM_KEY);
	}


	public boolean isInterfaceAvailable(TransductorInterfaceTypeEnum interfaceType) {
		return getAvailableTransductorInterfaces().contains(interfaceType.toString());
	}


	public String getMqttInterfaceBroker() {
		return applicationProperties.getProperty(PROPERTIES_MQTT_INTERFACE_BROKER_KEY);
	}


}
