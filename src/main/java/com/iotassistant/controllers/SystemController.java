package com.iotassistant.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.CameraCapabilitiesDTO;
import com.iotassistant.controllers.dtos.ChartCapabilitiesDTO;
import com.iotassistant.controllers.dtos.ServerStatusDTO;
import com.iotassistant.controllers.dtos.ServersStatusDTO;
import com.iotassistant.controllers.dtos.NotificationsCapabilitiesDTO;
import com.iotassistant.controllers.dtos.SystemCapabilitiesDTO;
import com.iotassistant.controllers.dtos.SystemDTO;
import com.iotassistant.controllers.dtos.TransductorCapabilitiesDTO;
import com.iotassistant.controllers.dtos.DevicesCapabilitiesDTO;
import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;
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
		NotificationsCapabilitiesDTO notificationsCapabilities = getNotificationCapabilities();
		return new SystemCapabilitiesDTO(devicesCapabilities,	serverssStatusDTO, chartCapabilities, notificationsCapabilities, ruleCapabilities);
	}

	@RequestMapping(value="/servers-status/", method = RequestMethod.GET)
	private ServersStatusDTO getServersStatus() {
		List<ServerStatusDTO> serversStatus = new ArrayList<ServerStatusDTO>();
		serversStatus.add(getMqttStatus());
		serversStatus.add(getTelegramStatus());
		return new ServersStatusDTO(serversStatus);
		
	}

	@RequestMapping(value="/notification-capabilities/", method = RequestMethod.GET)
	private NotificationsCapabilitiesDTO getNotificationCapabilities() {
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
	

	@RequestMapping(value="/mqtt-status/", method = RequestMethod.GET)
	public ServerStatusDTO getMqttStatus()  {	
		boolean isMqttConnected = systemService.isInterfaceConnected(TransductorInterfaceTypeEnum.MQTT);
		String mqttBroker = systemService.getMqttBroker();
		return new ServerStatusDTO( TransductorInterfaceTypeEnum.MQTT.toString(), isMqttConnected, "Broker: " + mqttBroker);
	}
	
	@RequestMapping(value="/telegram-status/", method = RequestMethod.GET)
	public ServerStatusDTO getTelegramStatus()  {	
		return new ServerStatusDTO( NotificationTypeEnum.TELEGRAM.toString(), systemService.isTelegramConnected(), "Bot: " + systemService.getBotUsername());
	}
	
	@RequestMapping(value="/chart-capabilities/", method = RequestMethod.GET)
	public ChartCapabilitiesDTO getChartCapabilities()  {	
		List<String> supportedChartTypes = systemService.getSupportedChartTypes();
		List<String> supportedChartIntervals = systemService.getSupportedChartIntervals();
		List<String> supportedSampleIntervals = systemService.getSupportedSampleIntervals();
		return new ChartCapabilitiesDTO(supportedChartTypes , supportedChartIntervals, supportedSampleIntervals);

	}
	
	@RequestMapping(value="/camera-capabilities/", method = RequestMethod.GET)
	public CameraCapabilitiesDTO getCameraCapabilities()  {	
		return new CameraCapabilitiesDTO(systemService.getSupportedCameraInterfaces(), systemService.getSupportedTransductorsWatchdogIntervals());
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
