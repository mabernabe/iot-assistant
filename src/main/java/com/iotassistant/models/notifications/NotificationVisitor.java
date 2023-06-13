package com.iotassistant.models.notifications;

public interface NotificationVisitor {
	
	public void visit(SensorRuleAlarmNotification sensorRuleAlarmNotification);
	
	public void visit(SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification);
	
	public void visit(SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification);
	
	public void visit(DeviceOfflineNotification offlineNotification);

	public void visit(SensorRuleCameraNotification sensorRuleCameraNotification);

}
