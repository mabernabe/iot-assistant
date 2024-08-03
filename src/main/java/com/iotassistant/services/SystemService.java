package com.iotassistant.services;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iotassistant.models.ServerStatus;
import com.iotassistant.models.devices.transductors.Property;
import com.iotassistant.utils.Date;

@Service
public class SystemService {
	
	@Value("${platform}")
	private String platform;
	
	@Autowired
	private TransductorsFacadeService transductorsService;
	
	@Autowired
	private CamerasService camerasService;
	
	@Autowired
	private GpsesService gpsesService;

	private @Autowired
	NotificationsService notificationsService;
	
	private @Autowired
	SensorRulesService sensorRulesService;
	
	@Autowired
	private ChartsService chartsService;
	
	@Autowired
	ServersStatusService serversStatusService;
	

	public List<Property> getSupportedSensorProperties() {
		return transductorsService.getSupportedPropertiesMeasured();
	}

	public List<String> getSupportedSensorInterfaces() {
		return this.transductorsService.getSupportedTransductorInterfaces();
	}

	public List<Property> getSupportedActuatorProperties() {
		return transductorsService.getSupportedPropertiesActuated();
	}

	public List<String> getSupportedActuatorInterfaces() {
		return this.transductorsService.getSupportedTransductorInterfaces();
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

	public List<String> getSupportedGpsesInterfaces() {
		return gpsesService.getSupportedInterfaces();
	}

	public List<String> getSupportedGpsesWatchdogIntervals() {
		return gpsesService.getSupportedWatchdogIntervals();
	}
	
	public List<ServerStatus> getServersStatus() {
		return serversStatusService.getServersStatus();
	}

	public String getUptime() {
		RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
		return Date.getTimeFromUptime(rb.getUptime());
	}





}
