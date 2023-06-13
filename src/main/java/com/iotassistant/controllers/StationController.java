package com.iotassistant.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.CameraCapabilitiesDTO;
import com.iotassistant.controllers.dtos.ChartCapabilitiesDTO;
import com.iotassistant.controllers.dtos.MQTTInterfaceCapabilitiesDTO;
import com.iotassistant.controllers.dtos.NotificationsCapabilitiesDTO;
import com.iotassistant.controllers.dtos.PinInterfaceCapabilitiesDTO;
import com.iotassistant.controllers.dtos.StationCapabilitiesDTO;
import com.iotassistant.controllers.dtos.StationDTO;
import com.iotassistant.controllers.dtos.TransductorCapabilitiesDTO;
import com.iotassistant.controllers.dtos.sensorrules.RuleCapabilitiesDTO;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;
import com.iotassistant.services.StationService;

@RestController
@RequestMapping("${station.uri}")
public class StationController {
	
	
	@Autowired
	private StationService stationService;
	

	@RequestMapping(value="/", method = RequestMethod.GET)
	public StationDTO getStation() {	
		StationCapabilitiesDTO stationCabalitiesDTO = buildStationCapabilitiesDTO();
		String platformName = stationService.getPlatformName();
		return new StationDTO(stationCabalitiesDTO, platformName);
	}

	@RequestMapping(value="/capabilities/", method = RequestMethod.GET)
	public StationCapabilitiesDTO getStationCapabilities()  {	
		return buildStationCapabilitiesDTO();
	}
	
	private StationCapabilitiesDTO buildStationCapabilitiesDTO()  {
		TransductorCapabilitiesDTO sensorCapabilities = getSensorCapabilities();
		TransductorCapabilitiesDTO actuatorCapabilities = getActuatorCapabilities();
		PinInterfaceCapabilitiesDTO pinInterfaceCapabilities = getPinInterfaceCapabilities();
		MQTTInterfaceCapabilitiesDTO mqttInterfaceCapabilities = getMqttInterfaceCapabilities();
		ChartCapabilitiesDTO chartCapabilities = getChartCapabilities();
		CameraCapabilitiesDTO cameraCapabilitiesDTO = getCameraCapabilities();
		RuleCapabilitiesDTO ruleCapabilities = getRuleCapabilities();
		NotificationsCapabilitiesDTO notificationsCapabilities = getNotificationCapabilities();
		boolean isTelegramConnected = stationService.isTelegramConnected();
		return new StationCapabilitiesDTO(sensorCapabilities, actuatorCapabilities, pinInterfaceCapabilities,  
				mqttInterfaceCapabilities, chartCapabilities, cameraCapabilitiesDTO, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
	}
	

	private CameraCapabilitiesDTO getCameraCapabilities() {
		return new CameraCapabilitiesDTO(stationService.getSupportedCameraInterfaces(), stationService.getSupportedWatchdogIntervals());
	}

	private NotificationsCapabilitiesDTO getNotificationCapabilities() {
		return new NotificationsCapabilitiesDTO(stationService.getSupportedNotificationTypes());
	}

	private ChartCapabilitiesDTO getChartCapabilities() {
		List<String> supportedChartTypes = stationService.getSupportedChartTypes();
		List<String> supportedChartIntervals = stationService.getSupportedChartIntervals();
		List<String> supportedSampleIntervals = stationService.getSupportedSampleIntervals();
		return new ChartCapabilitiesDTO(supportedChartTypes , supportedChartIntervals, supportedSampleIntervals);
	}

	
	private RuleCapabilitiesDTO getRuleCapabilities() {
		List<String> sensorSupportedRulesTypes = stationService.getSupportedSensorRulesTypes();
		List<String> supportedSensorRulesTimeBetweenTriggers = stationService.getSupportedSensorRulesTimeBetweenTriggers();
		return new RuleCapabilitiesDTO(sensorSupportedRulesTypes, supportedSensorRulesTimeBetweenTriggers);
		
	}

	private TransductorCapabilitiesDTO getSensorCapabilities() {
		List<Property> sensorSupportedProperties = stationService.getSupportedSensorProperties();
		List<String> sensorSupportedInterfaces = stationService.getSupportedSensorInterfaces();
		List<String> sensorSupportedWatchdogsIntervals =  stationService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(sensorSupportedProperties, sensorSupportedInterfaces, sensorSupportedWatchdogsIntervals);

	}
	
	private TransductorCapabilitiesDTO getActuatorCapabilities() {
		List<Property> actuatorSupportedProperties = stationService.getSupportedActuatorProperties();
		List<String> actuatorSupportedInterfaces = stationService.getSupportedActuatorInterfaces();
		List<String> actuatorSupportedWatchdogIntervals = stationService.getSupportedWatchdogIntervals();
		return new TransductorCapabilitiesDTO(actuatorSupportedProperties, actuatorSupportedInterfaces, actuatorSupportedWatchdogIntervals);

	}
	
	private PinInterfaceCapabilitiesDTO getPinInterfaceCapabilities()  {
		PinInterfaceCapabilitiesDTO pinInterfaceCapabilities = new PinInterfaceCapabilitiesDTO(false, new ArrayList<String>(), new ArrayList<String>(), stationService.getPlatformName());
		if (stationService.isInterfaceAvailable(TransductorInterfaceTypeEnum.PIN)) {
			pinInterfaceCapabilities = new PinInterfaceCapabilitiesDTO(stationService.isInterfaceAvailable(TransductorInterfaceTypeEnum.PIN), stationService.getAvailableAnalogPins(), stationService.getAvailableDigitalPins(), stationService.getPlatformPinInterfaceName());
		}
		return pinInterfaceCapabilities;
	}
	
	private MQTTInterfaceCapabilitiesDTO getMqttInterfaceCapabilities() {
		boolean isMqttInterfaceAvailable = stationService.isInterfaceAvailable(TransductorInterfaceTypeEnum.MQTT);
		String connectedBroker = stationService.getMqttInterfaceBroker();
		return new MQTTInterfaceCapabilitiesDTO( isMqttInterfaceAvailable, connectedBroker);
	}
	
	@RequestMapping(value="/powerOff/", method = RequestMethod.POST)
	public void powerOff() throws SystemCantShutdownException {	
		stationService.powerOff();
	}

}
