package com.iotassistant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.CameraCapabilitiesDTO;
import com.iotassistant.controllers.dtos.ChartCapabilitiesDTO;
import com.iotassistant.controllers.dtos.MQTTInterfaceCapabilitiesDTO;
import com.iotassistant.controllers.dtos.NotificationsCapabilitiesDTO;
import com.iotassistant.controllers.dtos.IotAssistantCapabilitiesDTO;
import com.iotassistant.controllers.dtos.IoTAssistantDTO;
import com.iotassistant.controllers.dtos.TransductorCapabilitiesDTO;
import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
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
		TransductorCapabilitiesDTO sensorCapabilities = getSensorCapabilities();
		TransductorCapabilitiesDTO actuatorCapabilities = getActuatorCapabilities();
		MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities = getMqttInterfaceCapabilities();
		ChartCapabilitiesDTO chartCapabilities = getChartCapabilities();
		CameraCapabilitiesDTO cameraCapabilitiesDTO = getCameraCapabilities();
		RuleCapabilitiesDTO ruleCapabilities = getRuleCapabilities();
		NotificationsCapabilitiesDTO notificationsCapabilities = getNotificationCapabilities();
		boolean isTelegramConnected = iotAssistantService.isTelegramConnected();
		return new IotAssistantCapabilitiesDTO(sensorCapabilities, actuatorCapabilities,  
				mqttInterfaceCapabilities, chartCapabilities, cameraCapabilitiesDTO, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
	}
	

	private CameraCapabilitiesDTO getCameraCapabilities() {
		return new CameraCapabilitiesDTO(iotAssistantService.getSupportedCameraInterfaces(), iotAssistantService.getSupportedWatchdogIntervals());
	}

	private NotificationsCapabilitiesDTO getNotificationCapabilities() {
		return new NotificationsCapabilitiesDTO(iotAssistantService.getSupportedNotificationTypes());
	}

	private ChartCapabilitiesDTO getChartCapabilities() {
		List<String> supportedChartTypes = iotAssistantService.getSupportedChartTypes();
		List<String> supportedChartIntervals = iotAssistantService.getSupportedChartIntervals();
		List<String> supportedSampleIntervals = iotAssistantService.getSupportedSampleIntervals();
		return new ChartCapabilitiesDTO(supportedChartTypes , supportedChartIntervals, supportedSampleIntervals);
	}

	
	private RuleCapabilitiesDTO getRuleCapabilities() {
		List<String> sensorSupportedRulesTypes = iotAssistantService.getSupportedSensorRulesTypes();
		List<String> supportedSensorRulesTimeBetweenTriggers = iotAssistantService.getSupportedSensorRulesTimeBetweenTriggers();
		return new RuleCapabilitiesDTO(sensorSupportedRulesTypes, supportedSensorRulesTimeBetweenTriggers);
		
	}

	private TransductorCapabilitiesDTO getSensorCapabilities() {
		List<Property> sensorSupportedProperties = iotAssistantService.getSupportedSensorProperties();
		List<String> sensorSupportedInterfaces = iotAssistantService.getSupportedSensorInterfaces();
		List<String> sensorSupportedWatchdogsIntervals =  iotAssistantService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(sensorSupportedProperties, sensorSupportedInterfaces, sensorSupportedWatchdogsIntervals);

	}
	
	private TransductorCapabilitiesDTO getActuatorCapabilities() {
		List<Property> actuatorSupportedProperties = iotAssistantService.getSupportedActuatorProperties();
		List<String> actuatorSupportedInterfaces = iotAssistantService.getSupportedActuatorInterfaces();
		List<String> actuatorSupportedWatchdogIntervals = iotAssistantService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(actuatorSupportedProperties, actuatorSupportedInterfaces, actuatorSupportedWatchdogIntervals);

	}
	
	
	private MQTTInterfaceCapabilitiesDTO getMqttInterfaceCapabilities() {
		boolean isMqttInterfaceAvailable = iotAssistantService.isInterfaceAvailable(TransductorInterfaceTypeEnum.MQTT);
		String mqttBroker = iotAssistantService.getMqttBroker();
		return new MQTTInterfaceCapabilitiesDTO( isMqttInterfaceAvailable, mqttBroker);
	}
	
	@RequestMapping(value="/powerOff/", method = RequestMethod.POST)
	public void powerOff() throws SystemCantShutdownException {	
		iotAssistantService.powerOff();
	}

}
