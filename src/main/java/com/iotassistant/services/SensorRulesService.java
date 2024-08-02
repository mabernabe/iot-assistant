package com.iotassistant.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.models.CameraSensorRule;
import com.iotassistant.models.SensorRule;
import com.iotassistant.models.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.SensorRuleType;
import com.iotassistant.models.TriggerActuatorSensorRule;
import com.iotassistant.models.devices.SensorValues;
import com.iotassistant.repositories.CameraSensorRuleJPARepository;
import com.iotassistant.repositories.SensorRuleJPARepository;
import com.iotassistant.repositories.TriggerActuatorSensorRuleJPARepository;

@Service
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
		return sensorRuleJPARepository.findOne(id);
	}

	public void deleteSensorRuleById(int id)  {
		sensorRuleJPARepository.delete(Integer.valueOf(id));	
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
			 if (sensorRule.isEnabled() && sensorRule.apply(sensorName, values)) {
				 new TriggerSensorRuleService(sensorRule, values).trigger();
			 }
		 }
		
	}
}
