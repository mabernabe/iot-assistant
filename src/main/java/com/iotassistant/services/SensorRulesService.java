package com.iotassistant.services;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.sensorrules.CameraSensorRule;
import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.sensorrules.SensorRuleType;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.repositories.CameraSensorRuleJPARepository;
import com.iotassistant.repositories.SensorRuleJPARepository;
import com.iotassistant.repositories.TriggerActuatorSensorRuleJPARepository;

@Service
@Transactional
public class SensorRulesService {

	@Autowired
	private SensorRuleJPARepository sensorRuleJPARepository;
	
	@Autowired
	private CameraSensorRuleJPARepository cameraSensorRuleJPARepository;
	
	@Autowired
	private TriggerActuatorSensorRuleJPARepository triggerActuatorSensorRuleJPARepository;

	private static SensorRulesService instance;

	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	
	public static SensorRulesService getInstance() {
		return instance;
	}
	
	public void newSensorRule(SensorRule sensorRule) {
		sensorRuleJPARepository.save(sensorRule);	
	}
	

	public List<SensorRule> getAllSensorRules() {
		return sensorRuleJPARepository.findAll();
	}


	public boolean existSensorRule(SensorRule sensorRule) {
		for (SensorRule installedSensorRule : sensorRuleJPARepository.findAll()) {
			if (sensorRule.equals(installedSensorRule)) {
				return true;
			}
		}
		return false;
	}

	public void enableDisableRule(boolean enable, int id) {
		assert(getSensorRule(id) != null);
		SensorRule sensorRule = this.getSensorRule(id);
		sensorRule.setEnabled(enable);
		sensorRuleJPARepository.saveAndFlush(sensorRule);	
	}

	public SensorRule getSensorRule(int id) {
		Optional<SensorRule> sensorRule = sensorRuleJPARepository.findById(id);
		if (sensorRule.isPresent()) {
			return sensorRule.get();
		}
		return null;
	}

	public void deleteSensorRuleById(int id)  {
		sensorRuleJPARepository.deleteById(Integer.valueOf(id));	
	}

	void deleteSensorRuleBySensorName(String sensorName) {
		List<SensorRule> allSensorRules = getAllSensorRules();
		for (SensorRule sensorRule : allSensorRules ) {
			if (sensorRule.getSensorName().equals(sensorName)) {
				deleteSensorRuleById(sensorRule.getId());
			}
		}		
	}


	public List<String> getSupportedSensorRuleTriggerIntervalEnum() {
		return SensorRuleTriggerIntervalEnum.getAvailableTriggerIntervalOptions();
	}


	void deleteTriggerActuatorSensorRules(String actuatorName)  {
		for (TriggerActuatorSensorRule triggerActuatorSensorRule :  triggerActuatorSensorRuleJPARepository.findAll() ) {
			if (triggerActuatorSensorRule.getActuatorName().equals(actuatorName)) {
				deleteSensorRuleById(triggerActuatorSensorRule.getId());
			}
		}		
	}
	
	void deleteCameraSensorRules(String cameraName)  {
		for (CameraSensorRule cameraSensorRule : cameraSensorRuleJPARepository.findAll() ) {
			if (cameraSensorRule.getCameraName().equals(cameraName)) {
				deleteSensorRuleById(cameraSensorRule.getId());
			}
		}		
	}
	
	public List<String> getSupportedSensorRulesTypes() {
		return SensorRuleType.getAllInstances();
	}

	void applyRules(String sensorName, SensorValues values) {
		 List<SensorRule> sensorRules = sensorRuleJPARepository.findAll();
		 for (SensorRule sensorRule : sensorRules) {
			 if (sensorRule.apply(sensorName, values)) {
				 new TriggerSensorRuleService(sensorRule, values).trigger();
			 }
		 }
		
	}
}
