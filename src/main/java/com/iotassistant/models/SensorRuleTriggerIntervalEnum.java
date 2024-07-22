package com.iotassistant.models;

import java.util.ArrayList;
import java.util.List;

public enum SensorRuleTriggerIntervalEnum {
	NO_INTERVAL("No interval", 0),
	ONE_MINUTES("1 minute", 1),
	FIVE_MINUTES("5 minutes", 5),
	HALF_HOUR("30 minutes", 30),
	ONE_HOUR("1 hour", 60),
	TWO_HOUR("2 hours", 120),
	FIVE_HOUR("5 hours", 300),
	TEN_HOUR("10 hours", 600),
	ONE_DAY("24 hours", 1440);
	
	private String string;
	
	private int minutes;

	private SensorRuleTriggerIntervalEnum(String string, int minutes) {
		this.string = string;
		this.minutes = minutes;
	}

	public String toString() {
		return string;
	}

	public int getMinutes() {
		return minutes;
	}

	public static List<String> getAvailableTriggerIntervalOptions() {
		List<String> availableOptions = new ArrayList<String>();
		for (SensorRuleTriggerIntervalEnum option : SensorRuleTriggerIntervalEnum.values()) {
			availableOptions.add(option.toString());
		}
		return availableOptions;
	}

	public static SensorRuleTriggerIntervalEnum getInstance(String string) {
		for (SensorRuleTriggerIntervalEnum timeBetweenTriggers : SensorRuleTriggerIntervalEnum.values()) {
			if (timeBetweenTriggers.toString().equals(string)) {
				return timeBetweenTriggers;
			}
		}
		return null;
	}


}
