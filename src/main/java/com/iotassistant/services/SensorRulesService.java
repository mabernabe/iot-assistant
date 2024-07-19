package com.iotassistant.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.sensorrules.AlarmSensorRule;
import com.iotassistant.models.sensorrules.CameraSensorRule;
import com.iotassistant.models.sensorrules.EnableRuleSensorRule;
import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.models.sensorrules.SensorRuleTriggerIntervalEnum;
import com.iotassistant.models.sensorrules.SensorRuleType;
import com.iotassistant.models.sensorrules.SensorRuleVisitor;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.repositories.RulesRepository;

@Service
@Transactional
public class SensorRulesService implements SensorRuleVisitor{

	private @Autowired
	RulesRepository rulesRepository;
	
	@Autowired
	private ActuatorsService actuatorsService;
	
	private @Autowired
	CamerasService camerasService;
	
	private @Autowired
	NotificationsService notificationsService;
	
	
	public void newSensorRule(SensorRule sensorRule) {
		rulesRepository.save(sensorRule);
		setupSensorRule(sensorRule);	
	}
	
	public void setupSensorRule(SensorRule sensorRule) {
		setupSensorRuleNotificationHandler(sensorRule);
		sensorRule.accept(this);		
	}

	
	private void setupSensorRuleNotificationHandler(SensorRule sensorRule) {
		NotificationHandler notificationHandler = notificationsService.getNotificationHandler(sensorRule.getNotificationType());
		sensorRule.setNotificationHandler(notificationHandler);	
	}


	public List<SensorRule> getAllSensorRules() {
		return rulesRepository.getAllSensorRules();
	}


	private SensorRule getSensorRule(SensorRule sensorRule) {
		List<SensorRule> allSensorRules = getAllSensorRules();
		SensorRule sensorRuleRet = null;
		for (SensorRule installedSensorRule : allSensorRules ) {
			if (installedSensorRule.equals(sensorRule)) {
				sensorRuleRet = installedSensorRule;
			}
		}
		return sensorRuleRet;
	}

	public boolean existSensorRule(SensorRule sensorRule) {
		return getSensorRule(sensorRule) != null;
	}

	public void enableDisableRule(boolean enable, int id) {
		SensorRule sensorRule = rulesRepository.getSensorRuleById(id);
		sensorRule.setEnabled(enable);
		rulesRepository.updateSensorRule(sensorRule);		
	}

	public SensorRule getSensorRule(int id) {
		return rulesRepository.getSensorRuleById(id);
	}

	public void deleteSensorRuleById(int id)  {
		rulesRepository.deleteById(Integer.valueOf(id));	
	}

	void deleteSensorRuleBySensorName(String sensorName) {
		List<SensorRule> allSensorRules = getAllSensorRules();
		for (SensorRule sensorRule : allSensorRules ) {
			if (sensorRule.getSensorName().equals(sensorName)) {
				deleteSensorRuleById(sensorRule.getId());
			}
		}		
	}

	@Override
	public void visit(EnableRuleSensorRule enableRuleSensorRule) {
		enableRuleSensorRule.setSensorRulesService(this);		
	}

	@Override
	public void visit(TriggerActuatorSensorRule triggerActuatorSensorRule) {
		triggerActuatorSensorRule.setActuatorsService(actuatorsService);	
	}
	
	@Override
	public void visit(AlarmSensorRule alarmSensorRule) {	
	}
	
	@Override
	public void visit(CameraSensorRule cameraSensorRule) {
		cameraSensorRule.setCamerasService(camerasService);
	}

	public List<String> getSupportedSensorRuleTriggerIntervalEnum() {
		return SensorRuleTriggerIntervalEnum.getAvailableTriggerIntervalOptions();
	}


	void deleteTriggerActuatorSensorRules(String actuatorName)  {
		List<TriggerActuatorSensorRule> triggerActuatorSensorRules = rulesRepository.getTriggerActuatorSensorRules();
		for (TriggerActuatorSensorRule triggerActuatorSensorRule : triggerActuatorSensorRules ) {
			if (triggerActuatorSensorRule.getActuatorName().equals(actuatorName)) {
				deleteSensorRuleById(triggerActuatorSensorRule.getId());
			}
		}		
	}
	
	void deleteCameraSensorRules(String cameraName)  {
		List<CameraSensorRule> cameraSensorRules = rulesRepository.getCameraSensorRules();
		for (CameraSensorRule cameraSensorRule : cameraSensorRules ) {
			if (cameraSensorRule.getCameraName().equals(cameraName)) {
				deleteSensorRuleById(cameraSensorRule.getId());
			}
		}		
	}
	
	public List<String> getSupportedSensorRulesTypes() {
		return SensorRuleType.getAllInstances();
	}

	void applyRules(String name, SensorValues values) {
		// TODO Auto-generated method stub
		
	}

	


	


}
