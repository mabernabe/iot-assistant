package com.iotassistant.services;

import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.notifications.SensorRuleAlarmNotification;
import com.iotassistant.models.notifications.SensorRuleEnableRuleNotification;
import com.iotassistant.models.notifications.SensorRuleTriggerActuatorNotification;
import com.iotassistant.models.sensorrules.AlarmSensorRule;
import com.iotassistant.models.sensorrules.CameraSensorRule;
import com.iotassistant.models.sensorrules.EnableRuleSensorRule;
import com.iotassistant.models.sensorrules.SensorRule;
import com.iotassistant.models.sensorrules.SensorRuleVisitor;
import com.iotassistant.models.sensorrules.TriggerActuatorSensorRule;
import com.iotassistant.models.transductor.SensorValues;

public class TriggerSensorRuleService implements SensorRuleVisitor{
	
	private SensorRule sensorRule;
	
	private SensorValues values;

	private NotificationHandler notificationHandler;

	public TriggerSensorRuleService(SensorRule sensorRule, SensorValues values) {
		super();
		this.sensorRule = sensorRule;
		this.values = values;
		this.notificationHandler = NotificationsService.getInstance().getNotificationHandler(sensorRule.getNotificationType());
	}

	public void trigger() {
		sensorRule.accept(this);
	}

	@Override
	public void visit(EnableRuleSensorRule enableRuleSensorRule) {
		SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification = new SensorRuleEnableRuleNotification(enableRuleSensorRule, values.getValue(sensorRule.getPropertyObserved()), values.getDate());
		boolean triggerIntervalReached = notificationHandler.handle(sensorRuleEnableRuleNotification);
		if (triggerIntervalReached) {			
			SensorRulesService.getInstance().enableDisableRule(enableRuleSensorRule.isEnableAction(), enableRuleSensorRule.getId());
		}	
	}

	@Override
	public void visit(TriggerActuatorSensorRule triggerActuatorSensorRule) {
		SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification = new SensorRuleTriggerActuatorNotification(triggerActuatorSensorRule, values.getValue(sensorRule.getPropertyObserved()), values.getDate());
		boolean triggerIntervalReached = notificationHandler.handle(sensorRuleTriggerActuatorNotification);
		if (triggerIntervalReached) {			
	//		try {
	//			actuatorsService.setActuatorValue(propertyActuated, actuatorName, actuatorSetValue);
	//		} catch (TransductorInterfaceException e) {
	//			e.printStackTrace();
	//		}
		}
		
	}

	@Override
	public void visit(AlarmSensorRule alarmSensorRule) {
		SensorRuleAlarmNotification SensorRuleAlarmNotification = new SensorRuleAlarmNotification(alarmSensorRule, values.getValue(sensorRule.getPropertyObserved()), values.getDate());
		notificationHandler.handle( SensorRuleAlarmNotification);	
	}

	@Override
	public void visit(CameraSensorRule cameraSensorRule) {
		// TODO Auto-generated method stub
		
	}

	

}
