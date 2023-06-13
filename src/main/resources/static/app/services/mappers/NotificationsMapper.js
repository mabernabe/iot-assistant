class NotificationsMapper {

	constructor() {
		this.sensorRulesMapper = new SensorRulesMapper();
	}
	
	buildNotificationsFromServiceObject(notificationsServiceObject) {		
		var offlineNotifications = this.#getOfflineNotificationsFromServiceObject(notificationsServiceObject.offlineNotifications);
		var sensorAlarmNotifications = this.#getSensorAlarmNotificationsFromServiceObject(notificationsServiceObject.sensorAlarmNotifications);
		var sensorEnableRuleNotifications = this.#getSensorEnableRuleNotificationsFromServiceObject(notificationsServiceObject.sensorRuleEnableRuleNotifications);
		var sensorRuleTriggerActuatorNotifications = this.#getSensorRuleTriggerActuatorNotificationsFromServiceObject(notificationsServiceObject.sensorRuleTriggerActuatorNotifications);
		var sensorRuleCameraNotifications = this.#getSensorRuleCameraNotificationsFromServiceObject(notificationsServiceObject.sensorRuleCameraNotifications);
		return new Notifications(offlineNotifications, sensorAlarmNotifications, sensorEnableRuleNotifications, sensorRuleTriggerActuatorNotifications, sensorRuleCameraNotifications);
	}
	
	#getOfflineNotificationsFromServiceObject(offlineNotificationsServiceObject) {
		var offlineNotifications = [];
		offlineNotificationsServiceObject.forEach(offlineNotificationObject => {
			var id = this.#getNotificationId(offlineNotificationObject);
			var date = this.#getNotificationDate(offlineNotificationObject);
			var transductorName = offlineNotificationObject.transductorName;
			var notification = new TransductorOfflineNotification(id, date, transductorName);
			offlineNotifications.push(notification);
		})
		return offlineNotifications;
	}
	
	#getNotificationId(serviceNotificationObject) {
		return serviceNotificationObject.id;		
	}
	
	#getSensorAlarmNotificationsFromServiceObject(sensorAlarmNotificationsServiceObject) {
		var sensorAlarmNotifications = [];
		sensorAlarmNotificationsServiceObject.forEach(sensorAlarmNotificationObject => {
			var id = this.#getNotificationId(sensorAlarmNotificationObject);
			var date = this.#getNotificationDate(sensorAlarmNotificationObject);
			var alarmSensorRule = this.sensorRulesMapper.buildAlarmSensorRuleFromServiceObject(sensorAlarmNotificationObject.alarmSensorRule);
			var sensorValue = this.#getSensorRuleNotificationSensorValue(sensorAlarmNotificationObject);
			var notification = new SensorRuleAlarmNotification(id, alarmSensorRule, sensorValue, date );
			sensorAlarmNotifications.push(notification);
		})
		return sensorAlarmNotifications;
	}
	
	#getNotificationDate(sensorRuleNotificationServiceObject) {
		return sensorRuleNotificationServiceObject.date;		
	}
	
	#getSensorEnableRuleNotificationsFromServiceObject(sensorRuleEnableRuleNotificationsServiceObject) {
		var sensorEnableRuleNotifications = [];
		sensorRuleEnableRuleNotificationsServiceObject.forEach(sensorRuleEnableRuleNotificationObject => {
			var id = this.#getNotificationId(sensorRuleEnableRuleNotificationObject);
			var date = this.#getNotificationDate(sensorRuleEnableRuleNotificationObject);
			var enableSensorRule = this.sensorRulesMapper.buildEnableSensorRuleFromServiceObject(sensorRuleEnableRuleNotificationObject.enableRuleSensorRule);
			var sensorValue = this.#getSensorRuleNotificationSensorValue(sensorRuleEnableRuleNotificationObject);
			var newSensorRuleState = sensorRuleEnableRuleNotificationObject.newSensorRuleState;
			var notification = new SensorRuleEnableRuleNotification(id, enableSensorRule, sensorValue, date , newSensorRuleState);
			sensorEnableRuleNotifications.push(notification);
		})
		return sensorEnableRuleNotifications;
	}
	
	#getSensorRuleTriggerActuatorNotificationsFromServiceObject(sensorRuleTriggerActuatorNotificationsServiceObject) {
		var sensorRuleTriggerActuatorNotifications = [];
		sensorRuleTriggerActuatorNotificationsServiceObject.forEach(sensorRuleTriggerActuatorNotificationObject => {
			var id = this.#getNotificationId(sensorRuleTriggerActuatorNotificationObject);
			var date = this.#getNotificationDate(sensorRuleTriggerActuatorNotificationObject);
			var triggerActuatorSensorRule = this.sensorRulesMapper.buildTriggerActuatorSensorRuleFromServiceObject(sensorRuleTriggerActuatorNotificationObject.triggerActuatorSensorRule);
			var sensorValue = this.#getSensorRuleNotificationSensorValue(sensorRuleTriggerActuatorNotificationObject);
			var notification = new SensorRuleTriggerActuatorNotification(id, triggerActuatorSensorRule, sensorValue, date  );
			sensorRuleTriggerActuatorNotifications.push(notification);
		})
		return sensorRuleTriggerActuatorNotifications;
	}

	#getSensorRuleNotificationSensorValue(sensorRuleNotificationServiceObject) {
		return sensorRuleNotificationServiceObject.sensorValue;		
	}
	
	#getSensorRuleCameraNotificationsFromServiceObject(sensorRuleCameraNotificationsServiceObject) {
		var sensorRuleCameraNotifications = [];
		sensorRuleCameraNotificationsServiceObject.forEach(sensorRuleCameraNotificationObject => {
			var id = this.#getNotificationId(sensorRuleCameraNotificationObject);
			var date = this.#getNotificationDate(sensorRuleCameraNotificationObject);
			var sensorValue = this.#getSensorRuleNotificationSensorValue(sensorRuleCameraNotificationObject);
			var cameraSensorRule = this.sensorRulesMapper.buildCameraSensorRuleFromServiceObject(sensorRuleCameraNotificationObject.cameraSensorRule);
			var pictureURL = sensorRuleCameraNotificationObject.pictureURL;
			var notification = new SensorRuleCameraNotification(id, cameraSensorRule, sensorValue, date, pictureURL);
			sensorRuleCameraNotifications.push(notification);
		})
		return sensorRuleCameraNotifications;
	}
	
	

}