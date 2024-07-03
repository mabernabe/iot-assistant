package com.iotassistant.controllers.dtos;

import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;

public class IotAssistantCapabilitiesDTO {

	TransductorCapabilitiesDTO sensorCapabilities;
	
	TransductorCapabilitiesDTO actuatorCapabilities;
		
	ChartCapabilitiesDTO chartCapabilities;
	
	CameraCapabilitiesDTO cameraCapabilities;
	
	RuleCapabilitiesDTO ruleCapabilities;
	
	NotificationsCapabilitiesDTO notificationsCapabilities;
	
	MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities;
		
	boolean isTelegramConnected;
	
	public IotAssistantCapabilitiesDTO(TransductorCapabilitiesDTO sensorCapabilities,
			TransductorCapabilitiesDTO actuatorCapabilities,
			MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities, ChartCapabilitiesDTO chartCapabilities, CameraCapabilitiesDTO cameraCapabilitiesDTO, 
			NotificationsCapabilitiesDTO notificationsCapabilities, RuleCapabilitiesDTO ruleCapabilities, boolean isTelegramConnected) {
		super();
		this.sensorCapabilities = sensorCapabilities;
		this.actuatorCapabilities = actuatorCapabilities;
		this.chartCapabilities = chartCapabilities;
		this.cameraCapabilities = cameraCapabilitiesDTO;
		this.notificationsCapabilities = notificationsCapabilities;
		this.mqttInterfaceCapabilities = mqttInterfaceCapabilities;
		this.isTelegramConnected = isTelegramConnected;
		this.ruleCapabilities = ruleCapabilities;
		
	}


	public TransductorCapabilitiesDTO getSensorCapabilities() {
		return sensorCapabilities;
	}


	public TransductorCapabilitiesDTO getActuatorCapabilities() {
		return actuatorCapabilities;
	}


	public ChartCapabilitiesDTO getChartCapabilities() {
		return chartCapabilities;
	}



	public CameraCapabilitiesDTO getCameraCapabilities() {
		return cameraCapabilities;
	}


	public MQTTInterfaceCapabilitiesDTO getMqttInterfaceCapabilities() {
		return mqttInterfaceCapabilities;
	}


	public RuleCapabilitiesDTO getRuleCapabilities() {
		return ruleCapabilities;
	}

	

	public NotificationsCapabilitiesDTO getNotificationsCapabilities() {
		return notificationsCapabilities;
	}


	public boolean isTelegramConnected() {
		return isTelegramConnected;
	}



	
	
	
	

}
