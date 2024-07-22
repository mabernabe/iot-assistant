package com.iotassistant.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.iotassistant.models.CameraSensorRule;
import com.iotassistant.models.SensorRule;
import com.iotassistant.models.TriggerActuatorSensorRule;

@Repository
@Scope("singleton")
public class RulesRepository {
	
	private Map<Integer, SensorRule> inMemorySensorRules = new HashMap<Integer, SensorRule>();

	@Autowired
	private SensorRuleJPARepository sensorRuleJPARepository;
	
	@Autowired
	private TriggerActuatorSensorRuleJPARepository triggerActuatorSensorRuleJPARepository;
	
	@Autowired
	private CameraSensorRuleJPARepository cameraSensorRuleJPARepository;
	
	@PostConstruct
	/* Populate in memory sensors map with the sensors in persistence */
    private void populateInMemorySensorRules() {
        List<SensorRule> allSensorRules = sensorRuleJPARepository.findAll();
        for (SensorRule sensorRule : allSensorRules) {
        	inMemorySensorRules.put(sensorRule.getId(), sensorRule);
        }
	}

	public void save(SensorRule sensorRule) {
		sensorRule = sensorRuleJPARepository.save(sensorRule);
		inMemorySensorRules.put(sensorRule.getId(), sensorRule);
	}

	public List<SensorRule> getAllSensorRules() {
		return new ArrayList<SensorRule>(inMemorySensorRules.values());
	}



	public SensorRule getSensorRuleById(int id) {
		return inMemorySensorRules.get(id);
	}

	public void deleteById(Integer id) {
		sensorRuleJPARepository.deleteById(id);
		inMemorySensorRules.remove(id);
		
	}

	public void updateSensorRule(SensorRule sensorRule) {
		sensorRuleJPARepository.save(sensorRule);		
	}

	public List<TriggerActuatorSensorRule> getTriggerActuatorSensorRules() {
		List<TriggerActuatorSensorRule> triggerActuatorSensorRules = new ArrayList<TriggerActuatorSensorRule>();
		for (TriggerActuatorSensorRule triggerActuatorSensorRule : triggerActuatorSensorRuleJPARepository.findAll()) {
			int id = triggerActuatorSensorRule.getId();
			triggerActuatorSensorRules.add((TriggerActuatorSensorRule) inMemorySensorRules.get(id));
		}
		return triggerActuatorSensorRules;
	}

	public List<CameraSensorRule> getCameraSensorRules() {
		List<CameraSensorRule> cameraSensorRules = new ArrayList<CameraSensorRule>();
		for (CameraSensorRule cameraSensorRule : cameraSensorRuleJPARepository.findAll()) {
			int id = cameraSensorRule.getId();
			cameraSensorRules.add((CameraSensorRule) inMemorySensorRules.get(id));
		}
		return cameraSensorRules;
	}

}
