package com.iotassistant.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.transductor.Actuator;

@Repository
@Scope("singleton")
public class ActuatorsRepository {
	
	private HashMap<String, Actuator> inMemoryActuators = new HashMap<String, Actuator>();
	
	@Autowired
	private ActuatorsJPARepository actuatorsJPARepository;
	
	@PostConstruct
	/* Populate in memory actuators map with the actuators in persistence */
    private void populateInMemorySensors() {
        List<Actuator> allActuators = actuatorsJPARepository.findAll();
        for (Actuator actuator : allActuators) {
        	inMemoryActuators.put(actuator.getName(), actuator);
        }
	}
	
	public Actuator getActuatorByName(String actuatorName) {
		return inMemoryActuators.get(actuatorName);
	}
	
	public List<Actuator> getAllActuators() {
		return new ArrayList<Actuator>(inMemoryActuators.values());
	}
	
	public Actuator save(Actuator actuator) {
		actuator = actuatorsJPARepository.save(actuator);
		inMemoryActuators.put(actuator.getName(), actuator);
		return actuator;
	}
	
	public void deleteByName(String name) {
		actuatorsJPARepository.deleteById(name);
		inMemoryActuators.remove(name);
		
	}
	
	public void update(Actuator actuator) {
		actuatorsJPARepository.save(actuator);
	}

}
