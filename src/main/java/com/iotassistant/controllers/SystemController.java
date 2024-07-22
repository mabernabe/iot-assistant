package com.iotassistant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.ChartCapabilitiesDTO;
import com.iotassistant.controllers.dtos.RuleCapabilitiesDTO;
import com.iotassistant.controllers.dtos.ServersStatusDTO;
import com.iotassistant.controllers.dtos.SystemCapabilitiesDTO;
import com.iotassistant.controllers.dtos.SystemDTO;
import com.iotassistant.controllers.dtos.devices.CameraCapabilitiesDTO;
import com.iotassistant.controllers.dtos.devices.DevicesCapabilitiesDTO;
import com.iotassistant.controllers.dtos.devices.transductors.TransductorCapabilitiesDTO;
import com.iotassistant.controllers.dtos.notifications.NotificationsCapabilitiesDTO;
import com.iotassistant.models.devices.Property;
import com.iotassistant.services.SystemCantShutdownException;
import com.iotassistant.services.SystemService;

@RestController
@RequestMapping("${system.uri}")
public class SystemController {
	
	
	@Autowired
	private SystemService systemService;
	

	@RequestMapping(value="/", method = RequestMethod.GET)
	public SystemDTO getSystem() {	
		SystemCapabilitiesDTO systemCabalitiesDTO = getSystemCapabilitiesDTO();
		String platformName = systemService.getPlatformName();
		return new SystemDTO(systemCabalitiesDTO, platformName);
	}

	@RequestMapping(value="/capabilities/", method = RequestMethod.GET)
	public SystemCapabilitiesDTO getSystemCapabilities()  {	
		return getSystemCapabilitiesDTO();
	}
	
	private SystemCapabilitiesDTO getSystemCapabilitiesDTO()  {
		DevicesCapabilitiesDTO devicesCapabilities = getDevicesCapabilities();
		ServersStatusDTO serverssStatusDTO = getServersStatus();
		ChartCapabilitiesDTO chartCapabilities = getChartCapabilities();
		RuleCapabilitiesDTO ruleCapabilities = getRuleCapabilities();
		NotificationsCapabilitiesDTO notificationsCapabilities = getNotificationsCapabilities();
		return new SystemCapabilitiesDTO(devicesCapabilities,	serverssStatusDTO, chartCapabilities, notificationsCapabilities, ruleCapabilities);
	}

	@RequestMapping(value="/servers-status/", method = RequestMethod.GET)
	private ServersStatusDTO getServersStatus() {
		return new ServersStatusDTO(systemService.getServersStatus());
		
	}

	@RequestMapping(value="/notifications-capabilities/", method = RequestMethod.GET)
	private NotificationsCapabilitiesDTO getNotificationsCapabilities() {
		return new NotificationsCapabilitiesDTO(systemService.getSupportedNotificationTypes());
	}
	
	@RequestMapping(value="/sensor-capabilities/", method = RequestMethod.GET)
	public TransductorCapabilitiesDTO getSensorCapabilities()  {	
		List<Property> sensorSupportedProperties = systemService.getSupportedSensorProperties();
		List<String> sensorSupportedInterfaces = systemService.getSupportedSensorInterfaces();
		List<String> sensorSupportedWatchdogsIntervals =  systemService.getSupportedTransductorsWatchdogIntervals();
		return new TransductorCapabilitiesDTO(sensorSupportedProperties, sensorSupportedInterfaces, sensorSupportedWatchdogsIntervals);

	}
	
	@RequestMapping(value="/actuator-capabilities/", method = RequestMethod.GET)
	public TransductorCapabilitiesDTO getActuatorCapabilities()  {	
		List<Property> actuatorSupportedProperties = systemService.getSupportedActuatorProperties();
		List<String> actuatorSupportedInterfaces = systemService.getSupportedActuatorInterfaces();
		List<String> actuatorSupportedWatchdogIntervals = systemService.getSupportedTransductorsWatchdogIntervals();
		return new TransductorCapabilitiesDTO(actuatorSupportedProperties, actuatorSupportedInterfaces, actuatorSupportedWatchdogIntervals);

	}
	
	@RequestMapping(value="/devices-capabilities/", method = RequestMethod.GET)
	public DevicesCapabilitiesDTO getDevicesCapabilities()  {
		return new DevicesCapabilitiesDTO(getSensorCapabilities(), getActuatorCapabilities(), this.getCameraCapabilities());
	}
	
	
	@RequestMapping(value="/charts-capabilities/", method = RequestMethod.GET)
	public ChartCapabilitiesDTO getChartCapabilities()  {	
		List<String> supportedChartTypes = systemService.getSupportedChartTypes();
		List<String> supportedChartIntervals = systemService.getSupportedChartIntervals();
		List<String> supportedSampleIntervals = systemService.getSupportedSampleIntervals();
		return new ChartCapabilitiesDTO(supportedChartTypes , supportedChartIntervals, supportedSampleIntervals);

	}
	
	@RequestMapping(value="/cameras-capabilities/", method = RequestMethod.GET)
	public CameraCapabilitiesDTO getCameraCapabilities()  {	
		return new CameraCapabilitiesDTO(systemService.getSupportedCameraInterfaces(), systemService.getSupportedCamerasWatchdogIntervals());
	}
	
	@RequestMapping(value="/rules-capabilities/", method = RequestMethod.GET)
	public RuleCapabilitiesDTO getRuleCapabilities()  {	
		List<String> sensorSupportedRulesTypes = systemService.getSupportedSensorRulesTypes();
		List<String> supportedSensorRulesTimeBetweenTriggers = systemService.getSupportedSensorRulesTimeBetweenTriggers();
		return new RuleCapabilitiesDTO(sensorSupportedRulesTypes, supportedSensorRulesTimeBetweenTriggers);
	}
	
	
	@RequestMapping(value="/powerOff/", method = RequestMethod.POST)
	public void powerOff() throws SystemCantShutdownException {	
		systemService.powerOff();
	}

}
