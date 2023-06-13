package com.iotassistant.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.PlatformInterfacesFactory;
import com.iotassistant.models.TelegramBotManager;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PlatformPinInterface;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceTypeEnum;

@Service
public class StationService {
	
	private PlatformInterfacesFactory platformInterfacesFactory = PlatformInterfacesFactory.getInstance();
	
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
	
	public List<String> getAvailableAnalogPins() {
		assert(this.isInterfaceAvailable(TransductorInterfaceTypeEnum.PIN) == true);
		PlatformPinInterface platformPinInterface = platformInterfacesFactory.getPlatformPinInterface() ;
		return getPinIdsToString(platformPinInterface.getAvailablePins(PinCapability.ANALOG));
	}
	
	private List<String> getPinIdsToString(List<PinId> pinIds) {
		List<String> pinIdsString = new ArrayList<>();
		for (PinId pinId: pinIds) {
			pinIdsString.add(pinId.toString());
		}
		return pinIdsString;
		
	}

	public List<String> getAvailableDigitalPins()  {
		assert(this.isInterfaceAvailable(TransductorInterfaceTypeEnum.PIN) == true);
		PlatformPinInterface platformPinInterface = platformInterfacesFactory.getPlatformPinInterface() ;
		return getPinIdsToString(platformPinInterface.getAvailablePins(PinCapability.DIGITAL));
	}
	
	public String getPlatformPinInterfaceName()  {
		assert(this.isInterfaceAvailable(TransductorInterfaceTypeEnum.PIN) == true);
		PlatformPinInterface platformPinInterface = platformInterfacesFactory.getPlatformPinInterface() ;
		return platformPinInterface.getPlatformName();
	}

	public List<Property> getSupportedSensorProperties() {
		return transductorService.getSupportedPropertiesMeasured();
	}

	public List<String> getSupportedSensorInterfaces() {
		return platformInterfacesFactory.getAvailableTransductorInterfaces();
	}

	public List<Property> getSupportedActuatorProperties() {
		return transductorService.getSupportedPropertiesActuated();
	}

	public List<String> getSupportedActuatorInterfaces() {
		return platformInterfacesFactory.getAvailableTransductorInterfaces();
	}

	public List<String> getSupportedNotificationTypes() {
		return notificationsService.getAvailableNotificationTypes();
	}
	
	public List<String> getSupportedSensorRuleTriggerIntervalEnum() {
		return sensorRulesService.getSupportedSensorRuleTriggerIntervalEnum();
	}
	
	public String getPlatformName() {
		return platformInterfacesFactory.getPlatformName();
	}

	public boolean isInterfaceAvailable(TransductorInterfaceTypeEnum interfaceType) {
		return platformInterfacesFactory.isInterfaceAvailable(interfaceType);
	}

	public String getMqttInterfaceBroker() {
		return platformInterfacesFactory.getMqttInterfaceBroker();
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
