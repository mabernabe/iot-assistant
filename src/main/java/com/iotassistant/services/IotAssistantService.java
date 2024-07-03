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
public class IotAssistantService {
	
	@Value("${mqtt.broker.url}")
	private String mqttBroker;
	
	@Value("${mqtt.folderpersistence}")
	private String mqttFolderPersistence;
	
	@Value("${mqtt.folderpersistence}")
	private String platform;
	
	@Autowired
	private TransductorsService transductorService;
	
	@Autowired
	private CamerasService cameraService;

	@Autowired
	NotificationsService notificationsService;
	
	@Autowired
	SensorRulesService sensorRulesService;
	
	@Autowired
	private ChartsService chartsService;
	
	@Autowired
	TelegramBotManager telegramBotManager;
	
	
	
	

	public List<Property> getSupportedSensorProperties() {
		return transductorService.getSupportedPropertiesMeasured();
	}

	public List<String> getSupportedSensorInterfaces() {
		return this.transductorService.getAvailableTransductorInterfaces();
	}

	public List<Property> getSupportedActuatorProperties() {
		return transductorService.getSupportedPropertiesActuated();
	}

	public List<String> getSupportedActuatorInterfaces() {
		return this.transductorService.getAvailableTransductorInterfaces();
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

	public boolean isInterfaceAvailable(TransductorInterfaceTypeEnum interfaceType) {
		return this.transductorService.getAvailableTransductorInterfaces().contains(interfaceType.toString());
	}

	public String getMqttBroker() {
		return this.mqttBroker;
	}

	public boolean isTelegramConnected() {
		return telegramBotManager.connected();
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

	public List<String> getSupportedWatchdogIntervals() {
		return transductorService.getSupportedWatchdogIntervals();
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
		return transductorService.getSupportedSensorRulesTypes();
	}


	public List<String> getSupportedSensorRulesTimeBetweenTriggers() {
		return transductorService.getSupportedSensorRulesTimeBetweenTriggers();
	}

	public List<String> getSupportedCameraInterfaces() {
		return cameraService.getSupportedInterfaces();
	}



}
