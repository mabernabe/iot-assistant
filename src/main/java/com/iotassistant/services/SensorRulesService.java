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
import com.iotassistant.models.sensorrules.SensorRuleVisitor;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.SensorValues;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.repositories.RulesRepository;

@Service
@Transactional
public class SensorRulesService implements SensorRuleVisitor{

	@Autowired
	RulesRepository rulesRepository;
	
	@Autowired
	SensorsService sensorsService;
	
	@Autowired
	ActuatorsService actuatorsService;
	
	@Autowired
	CamerasService camerasService;
	
	@Autowired
	NotificationsService notificationsService;
	
	
	public void newSensorRule(SensorRule sensorRule) throws TransductorInterfaceException {
		rulesRepository.save(sensorRule);
		setupSensorRule(sensorRule);	
	}
	
	public void setupSensorRule(SensorRule sensorRule) throws TransductorInterfaceException {
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


	public SensorRule getSensorRule(SensorRule sensorRule) {
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

	public void deleteSensorRuleById(int id) throws TransductorInterfaceException {
		rulesRepository.deleteById(Integer.valueOf(id));	
	}

	public void deleteSensorRuleBySensorName(String sensorName) throws TransductorInterfaceException {
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

	public boolean existSensorRule(int sensorRuleId) {
		return getSensorRule(sensorRuleId) != null;	
	}

	public void deleteTriggerActuatorSensorRules(String actuatorName) throws TransductorInterfaceException {
		List<TriggerActuatorSensorRule> triggerActuatorSensorRules = rulesRepository.getTriggerActuatorSensorRules();
		for (TriggerActuatorSensorRule triggerActuatorSensorRule : triggerActuatorSensorRules ) {
			if (triggerActuatorSensorRule.getActuatorName().equals(actuatorName)) {
				deleteSensorRuleById(triggerActuatorSensorRule.getId());
			}
		}		
	}
	
	public void deleteCameraSensorRules(String cameraName) throws TransductorInterfaceException  {
		List<CameraSensorRule> cameraSensorRules = rulesRepository.getCameraSensorRules();
		for (CameraSensorRule cameraSensorRule : cameraSensorRules ) {
			if (cameraSensorRule.getCameraName().equals(cameraName)) {
				deleteSensorRuleById(cameraSensorRule.getId());
			}
		}		
	}

	public void applyRules(String name, SensorValues values) {
		// TODO Auto-generated method stub
		
	}

	


	


}
