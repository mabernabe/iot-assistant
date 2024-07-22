package com.iotassistant.models.devices;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.iotassistant.utils.Date;

public enum WatchdogInterval {
	NO_WATCHDOG("Dont watch", 0),
	ONE_MINUTE("1 minute", 1),
	FIVE_MINUTES("5 minutes", 5),
	FIVETEEN_MINUTES("15 minutes", 15),
	HALF_HOUR("30 minutes", 30),
	ONE_HOUR("1 hour", 60),
	TWO_HOURS("2 hours", 120),
	FIVE_HOURS("5 hours", 300),
	TEN_HOURS("10 hours", 600),
	ONE_DAY("24 hours", 1440);
	
	public static final int WATCHDOG_MINIMUM_INTERVAL_SEC = 60 * 1000;
	
	private String string;
	
	private int minutes;

	private WatchdogInterval(String string, int minutes) {
		this.string = string;
		this.minutes = minutes;
	}

	public String toString() {
		return string;
	}

	public int getMinutes() {
		return minutes;
	}

	public static List<String> getAvailableWatchdogIntervalOptions() {
		List<String> availableOptions = new ArrayList<String>();
		for (WatchdogInterval option : WatchdogInterval.values()) {
			availableOptions.add(option.toString());
		}
		return availableOptions;
	}

	public static WatchdogInterval getInstance(String string) {
		for (WatchdogInterval watchdogInterval : WatchdogInterval.values()) {
			if (watchdogInterval.toString().equals(string)) {
				return watchdogInterval;
			}
		}
		return null;
	}
	
	public boolean isWatchdogIntervalReached(String date) throws ParseException {
		int interval = minutes + 1; 
		return Date.havePassedMinutesBetweenDates(date, Date.getCurrentDate(), interval);
		
	}
	
}
