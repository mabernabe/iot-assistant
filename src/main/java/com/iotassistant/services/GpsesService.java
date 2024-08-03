package com.iotassistant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.devices.Gps;
import com.iotassistant.models.devices.GpsInterfaceTypeEnum;
import com.iotassistant.models.devices.GpsPosition;
import com.iotassistant.models.devices.WatchdogInterval;
import com.iotassistant.repositories.GpsJPARepository;

@Service
public class GpsesService extends DeviceService{
	
	@Autowired
	private GpsJPARepository gpsesRepository;

	public void setUpInterface(Gps gps) {
		new DeviceSetUpInterfaceService().setUp(gps.getInterface());		
	}

	public void update(String gpsName, GpsPosition position) {
		Gps gps = this.getGpsByName(gpsName);
		assert(gps!=null);
		gps.setPosition(position);
		gps.setActive(true);
		gpsesRepository.saveAndFlush(gps);
		
	}

	public Gps getGpsByName(String name)  {
		return gpsesRepository.findOne(name);
	}

	public List<String> getSupportedInterfaces() {
		return GpsInterfaceTypeEnum.getAllInstances();
	}

	public List<String> getSupportedWatchdogIntervals() {
		return WatchdogInterval.getAvailableWatchdogIntervalOptions();
	}

	public List<Gps> getAllGpses() {
		return gpsesRepository.findAll();
	}

	public boolean exist(String name) {
		return getGpsByName(name) != null;
	}

	public void newGps(Gps gps) {
		gpsesRepository.saveAndFlush(gps);
		this.setUpInterface(gps);
	}

	public void deleteGpsByName(String name) {
		gpsesRepository.delete(name);
	}

}
