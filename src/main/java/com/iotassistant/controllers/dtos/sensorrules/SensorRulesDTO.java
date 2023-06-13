package com.iotassistant.controllers.dtos.sensorrules;

import java.util.ArrayList;
import java.util.List;

import com.iotassistant.models.sensorrules.AlarmSensorRule;
import com.iotassistant.models.sensorrules.CameraSensorRule;
import com.iotassistant.models.sensorrules.EnableRuleSensorRule;
import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.models.sensorrules.SensorRuleVisitor;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;

public class SensorRulesDTO implements SensorRuleVisitor{
	
	private ArrayList<AlarmSensorRuleDTO> alarmSensorRules = new ArrayList<AlarmSensorRuleDTO>();
	
	private ArrayList<EnableRuleSensorRuleDTO> enableSensorRules = new ArrayList<EnableRuleSensorRuleDTO>();

	private ArrayList<TriggerActuatorSensorRuleDTO> triggerActuatorSensorRules = new ArrayList<TriggerActuatorSensorRuleDTO>();

	private ArrayList<CameraSensorRuleDTO> cameraSensorRules = new ArrayList<CameraSensorRuleDTO>();

	
	public SensorRulesDTO() {
		super();
	}
	
	public SensorRulesDTO(List<SensorRule> sensorRules) {
		super();
		for (int i = 0; i < sensorRules.size(); i++) {
			sensorRules.get(i).accept(this);
		}
	}


	public ArrayList<AlarmSensorRuleDTO> getAlarmSensorRules() {
		return alarmSensorRules;
	}

	public ArrayList<EnableRuleSensorRuleDTO> getEnableSensorRules() {
		return enableSensorRules;
	}

	public ArrayList<TriggerActuatorSensorRuleDTO> getTriggerActuatorSensorRules() {
		return triggerActuatorSensorRules;
	}

	public ArrayList<CameraSensorRuleDTO> getCameraSensorRules() {
		return cameraSensorRules;
	}

	@Override
	public void visit(EnableRuleSensorRule enableRuleSensorRule) {
		this.enableSensorRules.add(new EnableRuleSensorRuleDTO(enableRuleSensorRule));
	}

	@Override
	public void visit(TriggerActuatorSensorRule triggerActuatorSensorRule) {
		this.triggerActuatorSensorRules.add(new TriggerActuatorSensorRuleDTO(triggerActuatorSensorRule));
		
	}

	@Override
	public void visit(AlarmSensorRule alarmSensorRule) {
		this.alarmSensorRules.add(new AlarmSensorRuleDTO(alarmSensorRule));		
	}

	@Override
	public void visit(CameraSensorRule cameraSensorRule) {
		this.cameraSensorRules.add(new CameraSensorRuleDTO(cameraSensorRule));	
		
	}
}
