package com.iotassistant.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.transductor.Sensor;

@Repository
@Scope("singleton")
public class SensorsRepository {
	
	private Map<String, Sensor> inMemorySensors = new HashMap<String, Sensor>();

	@Autowired
	private SensorsJPARepository sensorsJPARepository;
	
	
	@PostConstruct
	/* Populate in memory sensors map with the sensors in persistence */
    private void populateInMemorySensors() {
        List<Sensor> allSensors = sensorsJPARepository.findAll();
        for (Sensor sensor : allSensors) {
        	inMemorySensors.put(sensor.getName(), sensor);
        }
	}
	
	public Sensor getSensorByName(String name) {
		return inMemorySensors.get(name);
	}
	
	public List<Sensor> getAllSensors() {
		return new ArrayList<Sensor>(inMemorySensors.values());
	}
	
	public Sensor save(Sensor sensor) {
		sensor = sensorsJPARepository.save(sensor);
		inMemorySensors.put(sensor.getName(), sensor);
		return sensor;
	}

	public void deleteSensorByName(String name) {
		sensorsJPARepository.deleteById(name);
		inMemorySensors.remove(name);	
	}
	
	public void update(Sensor sensor) {
		sensorsJPARepository.save(sensor);
	}
}
