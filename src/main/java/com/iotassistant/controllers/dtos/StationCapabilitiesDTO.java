package com.iotassistant.controllers.dtos;

import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;

public class StationCapabilitiesDTO {

	TransductorCapabilitiesDTO sensorCapabilities;
	
	TransductorCapabilitiesDTO actuatorCapabilities;
	
	PinInterfaceCapabilitiesDTO pinInterfaceCapabilities;
		
	ChartCapabilitiesDTO chartCapabilities;
	
	CameraCapabilitiesDTO cameraCapabilities;
	
	RuleCapabilitiesDTO ruleCapabilities;
	
	NotificationsCapabilitiesDTO notificationsCapabilities;
	
	MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities;
		
	boolean isTelegramConnected;
	
	public StationCapabilitiesDTO(TransductorCapabilitiesDTO sensorCapabilities,
			TransductorCapabilitiesDTO actuatorCapabilities, PinInterfaceCapabilitiesDTO pinInterfaceCapabilities,
			MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities, ChartCapabilitiesDTO chartCapabilities, CameraCapabilitiesDTO cameraCapabilitiesDTO, 
			NotificationsCapabilitiesDTO notificationsCapabilities, RuleCapabilitiesDTO ruleCapabilities, boolean isTelegramConnected) {
		super();
		this.sensorCapabilities = sensorCapabilities;
		this.actuatorCapabilities = actuatorCapabilities;
		this.pinInterfaceCapabilities = pinInterfaceCapabilities;
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


	public PinInterfaceCapabilitiesDTO getPinInterfaceCapabilities() {
		return pinInterfaceCapabilities;
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
