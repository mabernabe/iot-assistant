package com.iotassistant.models.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotassistant.models.TelegramBotManager;
import com.iotassistant.repositories.NotificationsRepository;

@Component
public class TelegramNotificationsHandler extends NotificationHandler  implements NotificationVisitor{
	
	private static final String TELEGRAM_MSG_END_OF_LINE = "\n";
	
	private static final String TELEGRAM_MSG_BOLD_TAG = "*";
	
	private static final String TELEGRAM_MSG_MONOSPACE_TAG = "`";

	@Autowired
	TelegramBotManager telegramBotManager;
	
	@Autowired
    public TelegramNotificationsHandler(NotificationsRepository notificationsRepository) {
        super(notificationsRepository);
    }
	
	@Override
	protected void sendNotification(Notification notification) {
		notification.accept(this);	
	}
	
	@Override
	public void visit(SensorRuleAlarmNotification sensorMeasureNotification)  {	
		try {
			String alarmTelegramNotificationMessage = buildTelegramAlarmSensorRuleNotificationMessage(sensorMeasureNotification);
			telegramBotManager.sendTelegramTextMessage(alarmTelegramNotificationMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	private String buildTelegramAlarmSensorRuleNotificationMessage(SensorRuleAlarmNotification alarmNotification)  {			
		String alarmNotificationText = buildTelegramSensorMeasureCommonMessage("ALARM SENSOR RULE NOTIFICATION", alarmNotification);
		alarmNotificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		return alarmNotificationText;
	}

	@Override
	public void visit(DeviceOfflineNotification offlineNotification) {
		try {
			String offlineTelegramNotificationMessage = buildTelegramOfflineNotificationMessage(offlineNotification);
			telegramBotManager.sendTelegramTextMessage(offlineTelegramNotificationMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private String buildTelegramOfflineNotificationMessage(DeviceOfflineNotification transductorOfflineNotification)  {			
		String notificationText = TELEGRAM_MSG_BOLD_TAG + "DEVICE OFFLINE NOTIFICATION" + TELEGRAM_MSG_BOLD_TAG + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		notificationText += "Device name: " + transductorOfflineNotification.getDeviceName() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "Date: " + transductorOfflineNotification.getDate() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		return notificationText;
	}

	@Override
	public void visit(SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification) {
		try {
			String sensorRuleSwitchAlarmTelegramNotificationMessage = buildTelegramEnableRuleSensorRuleNotificationMessage(sensorRuleEnableRuleNotification);
			telegramBotManager.sendTelegramTextMessage(sensorRuleSwitchAlarmTelegramNotificationMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String buildTelegramEnableRuleSensorRuleNotificationMessage(SensorRuleEnableRuleNotification sensorRuleEnableRuleNotification) {
		String newSensorRuleState = (sensorRuleEnableRuleNotification.isSensorRuleNewState())? "Enabled" : "Disabled";
		String notificationText = buildTelegramSensorMeasureCommonMessage(newSensorRuleState.toUpperCase() + " SENSOR ENABLE RULE NOTIFICATION", sensorRuleEnableRuleNotification);
		notificationText += "Sensor rule ID: " + sensorRuleEnableRuleNotification.getEnableSensorRule().getSensorRuleId() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "New sensor rule state: " + newSensorRuleState  + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		return notificationText;
	}
	
	private String buildTelegramSensorMeasureCommonMessage(String notificationName, SensorMeasureNotification sensorMeasureNotification) {
		String notificationText = TELEGRAM_MSG_BOLD_TAG + notificationName + TELEGRAM_MSG_BOLD_TAG + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		notificationText += "Sensor: " + sensorMeasureNotification.getSensorName() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "Property: " + sensorMeasureNotification.getPropertyObserved().getNameWithUnit() + TELEGRAM_MSG_END_OF_LINE;
		String analogThresholdOperator = (sensorMeasureNotification.getPropertyObserved().isDigital())? "" : sensorMeasureNotification.getValueThresholdOperator().toString(); 
		notificationText += "Measure threshold: " + analogThresholdOperator + sensorMeasureNotification.getValueThresholdObserved() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "Measured: " + sensorMeasureNotification.getSensorValue() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "Date: " + sensorMeasureNotification.getDate() + TELEGRAM_MSG_END_OF_LINE;
		return notificationText;
	}

	@Override
	public void visit(SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification) {
		try {
			String sensorRuleTriggerActuatorTelegramNotificationMessage = buildTelegramTriggerActuatorSensorRuleNotificationMessage(sensorRuleTriggerActuatorNotification);
			telegramBotManager.sendTelegramTextMessage(sensorRuleTriggerActuatorTelegramNotificationMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String buildTelegramTriggerActuatorSensorRuleNotificationMessage(
			SensorRuleTriggerActuatorNotification sensorRuleTriggerActuatorNotification) {
		String notificationText = buildTelegramSensorMeasureCommonMessage("SENSOR RULE TRIGGER ACTUATOR NOTIFICATION", sensorRuleTriggerActuatorNotification);
		notificationText += "Actuator name: " + sensorRuleTriggerActuatorNotification.getSensorRule().getActuatorName() + TELEGRAM_MSG_END_OF_LINE;
		String newActuatorValue = (sensorRuleTriggerActuatorNotification.getSensorRule().getActuatorSetValue());
		notificationText += "Actuator set value: " + newActuatorValue  + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		return notificationText;
	}

	@Override
	public void visit(SensorRuleCameraNotification sensorRuleCameraNotification) {
		try {
			String sensorRuleCameraTelegramNotificationMessage = buildTelegramCameraSensorRuleNotificationMessage(sensorRuleCameraNotification);
			telegramBotManager.sendTelegramTextMessage(sensorRuleCameraTelegramNotificationMessage);
			if (sensorRuleCameraNotification.getPicture() != null) {
				telegramBotManager.sendTelegramPictureMessage("Picture", sensorRuleCameraNotification.getPicture());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private String buildTelegramCameraSensorRuleNotificationMessage(
			SensorRuleCameraNotification sensorRuleCameraNotification) {
		String notificationText = buildTelegramSensorMeasureCommonMessage("SENSOR RULE CAMERA NOTIFICATION", sensorRuleCameraNotification);
		notificationText += "Camera name: " + sensorRuleCameraNotification.getCameraSensorRule().getCameraName() + TELEGRAM_MSG_END_OF_LINE;
		notificationText += "Picture: "  + sensorRuleCameraNotification.getPicture() == null ? "Could not get image from camera" : "" + TELEGRAM_MSG_END_OF_LINE;
		notificationText += TELEGRAM_MSG_MONOSPACE_TAG;
		return notificationText;
	}

	
}
