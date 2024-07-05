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
import com.iotassistant.controllers.dtos.IotAssistantCapabilitiesDTO;
import com.iotassistant.controllers.dtos.IoTAssistantDTO;
import com.iotassistant.controllers.dtos.TransductorCapabilitiesDTO;
import com.iotassistant.controllers.dtos.DevicesCapabilitiesDTO;
import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;
import com.iotassistant.services.IotAssistantService;

@RestController
@RequestMapping("${iotassistant.uri}")
public class IotAssistantController {
	
	
	@Autowired
	private IotAssistantService iotAssistantService;
	

	@RequestMapping(value="/", method = RequestMethod.GET)
	public IoTAssistantDTO getIotAssistant() {	
		IotAssistantCapabilitiesDTO iotAssistantCabalitiesDTO = getIotAssistantCapabilitiesDTO();
		String platformName = iotAssistantService.getPlatformName();
		return new IoTAssistantDTO(iotAssistantCabalitiesDTO, platformName);
	}

	@RequestMapping(value="/capabilities/", method = RequestMethod.GET)
	public IotAssistantCapabilitiesDTO getIotAssistantCapabilities()  {	
		return getIotAssistantCapabilitiesDTO();
	}
	
	private IotAssistantCapabilitiesDTO getIotAssistantCapabilitiesDTO()  {
		DevicesCapabilitiesDTO devicesCapabilities = getDevicesCapabilities();
		ServersStatusDTO serverssStatusDTO = getServersStatus();
		ChartCapabilitiesDTO chartCapabilities = getChartCapabilities();
		RuleCapabilitiesDTO ruleCapabilities = getRuleCapabilities();
		NotificationsCapabilitiesDTO notificationsCapabilities = getNotificationCapabilities();
		return new IotAssistantCapabilitiesDTO(devicesCapabilities,	serverssStatusDTO, chartCapabilities, notificationsCapabilities, ruleCapabilities);
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
		return new NotificationsCapabilitiesDTO(iotAssistantService.getSupportedNotificationTypes());
	}
	
	@RequestMapping(value="/sensor-capabilities/", method = RequestMethod.GET)
	public TransductorCapabilitiesDTO getSensorCapabilities()  {	
		List<Property> sensorSupportedProperties = iotAssistantService.getSupportedSensorProperties();
		List<String> sensorSupportedInterfaces = iotAssistantService.getSupportedSensorInterfaces();
		List<String> sensorSupportedWatchdogsIntervals =  iotAssistantService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(sensorSupportedProperties, sensorSupportedInterfaces, sensorSupportedWatchdogsIntervals);

	}
	
	@RequestMapping(value="/actuator-capabilities/", method = RequestMethod.GET)
	public TransductorCapabilitiesDTO getActuatorCapabilities()  {	
		List<Property> actuatorSupportedProperties = iotAssistantService.getSupportedActuatorProperties();
		List<String> actuatorSupportedInterfaces = iotAssistantService.getSupportedActuatorInterfaces();
		List<String> actuatorSupportedWatchdogIntervals = iotAssistantService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(actuatorSupportedProperties, actuatorSupportedInterfaces, actuatorSupportedWatchdogIntervals);

	}
	
	@RequestMapping(value="/devices-capabilities/", method = RequestMethod.GET)
	public DevicesCapabilitiesDTO getDevicesCapabilities()  {
		return new DevicesCapabilitiesDTO(getSensorCapabilities(), getActuatorCapabilities(), this.getCameraCapabilities());
	}
	

	@RequestMapping(value="/mqtt-status/", method = RequestMethod.GET)
	public ServerStatusDTO getMqttStatus()  {	
		boolean isMqttConnected = iotAssistantService.isInterfaceConnected(TransductorInterfaceTypeEnum.MQTT);
		String mqttBroker = iotAssistantService.getMqttBroker();
		return new ServerStatusDTO( TransductorInterfaceTypeEnum.MQTT.toString(), isMqttConnected, "Broker: " + mqttBroker);
	}
	
	@RequestMapping(value="/telegram-status/", method = RequestMethod.GET)
	public ServerStatusDTO getTelegramStatus()  {	
		return new ServerStatusDTO( NotificationTypeEnum.TELEGRAM.toString(), iotAssistantService.isTelegramConnected(), "Bot: " + iotAssistantService.getBotUsername());
	}
	
	@RequestMapping(value="/chart-capabilities/", method = RequestMethod.GET)
	public ChartCapabilitiesDTO getChartCapabilities()  {	
		List<String> supportedChartTypes = iotAssistantService.getSupportedChartTypes();
		List<String> supportedChartIntervals = iotAssistantService.getSupportedChartIntervals();
		List<String> supportedSampleIntervals = iotAssistantService.getSupportedSampleIntervals();
		return new ChartCapabilitiesDTO(supportedChartTypes , supportedChartIntervals, supportedSampleIntervals);

	}
	
	@RequestMapping(value="/camera-capabilities/", method = RequestMethod.GET)
	public CameraCapabilitiesDTO getCameraCapabilities()  {	
		return new CameraCapabilitiesDTO(iotAssistantService.getSupportedCameraInterfaces(), iotAssistantService.getSupportedWatchdogIntervals());
	}
	
	@RequestMapping(value="/rules-capabilities/", method = RequestMethod.GET)
	public RuleCapabilitiesDTO getRuleCapabilities()  {	
		List<String> sensorSupportedRulesTypes = iotAssistantService.getSupportedSensorRulesTypes();
		List<String> supportedSensorRulesTimeBetweenTriggers = iotAssistantService.getSupportedSensorRulesTimeBetweenTriggers();
		return new RuleCapabilitiesDTO(sensorSupportedRulesTypes, supportedSensorRulesTimeBetweenTriggers);
	}
	
	
	@RequestMapping(value="/powerOff/", method = RequestMethod.POST)
	public void powerOff() throws SystemCantShutdownException {	
		iotAssistantService.powerOff();
	}

}
