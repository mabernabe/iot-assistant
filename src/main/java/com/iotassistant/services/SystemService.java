package com.iotassistant.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iotassistant.models.TelegramBotManager;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;

@Service
public class SystemService {
	
	@Value("${mqtt.broker.url}")
	private String mqttBroker;
	
	@Value("${mqtt.folderpersistence}")
	private String mqttFolderPersistence;
	
	@Value("${mqtt.folderpersistence}")
	private String platform;
	
	@Autowired
	private TransductorsService transductorsService;
	
	@Autowired
	private CamerasService camerasService;

	private @Autowired
	NotificationsService notificationsService;
	
	private @Autowired
	SensorRulesService sensorRulesService;
	
	@Autowired
	private ChartsService chartsService;
	
	private @Autowired
	TelegramBotManager telegramBotManager;
	
	
	
	

	public List<Property> getSupportedSensorProperties() {
		return transductorsService.getSupportedPropertiesMeasured();
	}

	public List<String> getSupportedSensorInterfaces() {
		return this.transductorsService.getConnectedTransductorInterfaces();
	}

	public List<Property> getSupportedActuatorProperties() {
		return transductorsService.getSupportedPropertiesActuated();
	}

	public List<String> getSupportedActuatorInterfaces() {
		return this.transductorsService.getConnectedTransductorInterfaces();
	}

	public List<String> getSupportedNotificationTypes() {
		return notificationsService.getAvailableNotificationTypes();
	}
	
	public List<String> getSupportedSensorRuleTriggerIntervalEnum() {
		return sensorRulesService.getSupportedSensorRuleTriggerIntervalEnum();
	}
	
	public String getPlatformName() {
		return this.platform;
	}

	public boolean isInterfaceConnected(TransductorInterfaceTypeEnum interfaceType) {
		return this.transductorsService.getConnectedTransductorInterfaces().contains(interfaceType.toString());
	}

	public String getMqttBroker() {
		return this.mqttBroker;
	}

	public boolean isTelegramConnected() {
		return telegramBotManager.connected();
	}
	
	public String getBotUsername() {
		return telegramBotManager.getBotUsername();
	}
	

	public List<String> getSupportedChartTypes() {
		return chartsService.getSupportedChartTypes();
	}

	public List<String> getSupportedChartIntervals() {
		return chartsService.getSupportedChartIntervals();
	}

	public List<String> getSupportedSampleIntervals() {
		return chartsService.getSupportedSampleIntervals();
	}

	public List<String> getSupportedTransductorsWatchdogIntervals() {
		return transductorsService.getSupportedWatchdogIntervals();
	}
	
	public List<String> getSupportedCamerasWatchdogIntervals() {
		return camerasService.getSupportedWatchdogIntervals();
	}

	public void powerOff() throws SystemCantShutdownException{
		String shutdownCommand = "shutdown.exe -s -t 0";
	    String operatingSystem = System.getProperty("os.name");
	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	        shutdownCommand = "/sbin/shutdown -h now";
	    }    
		try {
			System.out.println("Shutting down the system after 5 seconds.");
			Runtime.getRuntime().exec(shutdownCommand);
		    System.exit(0);
		}
		catch(IOException e) {
			throw new SystemCantShutdownException(e.getLocalizedMessage());
		}
	}
	

	public List<String> getSupportedSensorRulesTypes() {
		return sensorRulesService.getSupportedSensorRulesTypes();
	}


	public List<String> getSupportedSensorRulesTimeBetweenTriggers() {
		return transductorsService.getSupportedSensorRulesTimeBetweenTriggers();
	}

	public List<String> getSupportedCameraInterfaces() {
		return camerasService.getSupportedInterfaces();
	}



}
