package com.iotassistant.controllers.dtos;

import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;

public class SystemCapabilitiesDTO {

	private DevicesCapabilitiesDTO devicesCapabilities;
		
	private ChartCapabilitiesDTO chartCapabilities;
	
	private RuleCapabilitiesDTO ruleCapabilities;
	
	private NotificationsCapabilitiesDTO notificationsCapabilities;
	
	private ServersStatusDTO serversStatus;
	
	public SystemCapabilitiesDTO(DevicesCapabilitiesDTO devicesCapabilities, ServersStatusDTO serversStatusDTO, 
			ChartCapabilitiesDTO chartCapabilities, NotificationsCapabilitiesDTO notificationsCapabilities, RuleCapabilitiesDTO ruleCapabilities) {
		super();
		this.devicesCapabilities = devicesCapabilities;
		this.chartCapabilities = chartCapabilities;
		this.notificationsCapabilities = notificationsCapabilities;
		this.serversStatus = serversStatusDTO;
		this.ruleCapabilities = ruleCapabilities;
		
	}

	public DevicesCapabilitiesDTO getDevicesCapabilities() {
		return devicesCapabilities;
	}


	public ChartCapabilitiesDTO getChartCapabilities() {
		return chartCapabilities;
	}


	public ServersStatusDTO getServersStatus() {
		return serversStatus;
	}

	public RuleCapabilitiesDTO getRuleCapabilities() {
		return ruleCapabilities;
	}

	

	public NotificationsCapabilitiesDTO getNotificationsCapabilities() {
		return notificationsCapabilities;
	}


}
