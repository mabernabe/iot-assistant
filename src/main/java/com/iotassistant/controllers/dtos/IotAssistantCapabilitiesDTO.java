package com.iotassistant.controllers.dtos;

import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;

public class IotAssistantCapabilitiesDTO {

	DevicesCapabilitiesDTO devicesCapabilities;
		
	ChartCapabilitiesDTO chartCapabilities;
	
	RuleCapabilitiesDTO ruleCapabilities;
	
	NotificationsCapabilitiesDTO notificationsCapabilities;
	
	ServersStatusDTO serversStatusDTO;
	
	public IotAssistantCapabilitiesDTO(DevicesCapabilitiesDTO devicesCapabilities, ServersStatusDTO serversStatusDTO, 
			ChartCapabilitiesDTO chartCapabilities, NotificationsCapabilitiesDTO notificationsCapabilities, RuleCapabilitiesDTO ruleCapabilities) {
		super();
		this.devicesCapabilities = devicesCapabilities;
		this.chartCapabilities = chartCapabilities;
		this.notificationsCapabilities = notificationsCapabilities;
		this.serversStatusDTO = serversStatusDTO;
		this.ruleCapabilities = ruleCapabilities;
		
	}

	public DevicesCapabilitiesDTO getDevicesCapabilities() {
		return devicesCapabilities;
	}


	public ChartCapabilitiesDTO getChartCapabilities() {
		return chartCapabilities;
	}


	public ServersStatusDTO getServersStatusDTO() {
		return serversStatusDTO;
	}

	public RuleCapabilitiesDTO getRuleCapabilities() {
		return ruleCapabilities;
	}

	

	public NotificationsCapabilitiesDTO getNotificationsCapabilities() {
		return notificationsCapabilities;
	}


}
