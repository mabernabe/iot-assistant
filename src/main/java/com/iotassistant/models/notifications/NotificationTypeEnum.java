package com.iotassistant.models.notifications;

import java.util.ArrayList;
import java.util.List;


public enum NotificationTypeEnum {
	TELEGRAM("TELEGRAM"),
	WEB("WEB");

	private String string;

	private NotificationTypeEnum(String string) {
		this.string = string;
	}


	@Override
	public String toString() {
		return string;
	}

	public static List<String >getAvailableNotificationTypes(boolean isTelegramAvailable) {
		List<String> availableTypes = new ArrayList<String>();
		for (NotificationTypeEnum notificationType : NotificationTypeEnum.values()) {
			availableTypes.add(notificationType.toString());
		}
		if (!isTelegramAvailable) {
			availableTypes.remove(TELEGRAM.toString());
		}
		return availableTypes;
	}


	public static NotificationTypeEnum getInstance(String string) {
		for (NotificationTypeEnum notificationType : NotificationTypeEnum.values()) {
			if (notificationType.toString().equals(string)) {
				return notificationType;
			}
		}
		return null;
	}

}
