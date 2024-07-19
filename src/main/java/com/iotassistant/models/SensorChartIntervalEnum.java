package com.iotassistant.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.iotassistant.utils.Date;

public enum SensorChartIntervalEnum {
	ONE_HOUR("1 hour", 60),
	EIGHT_HOURS("8 hours", 480),
	ONE_DAY("1 day", 1440),
	TWO_DAYS("2 days", 2880),
	ONE_WEEK("1 week", 10080),
	ONE_MONTH("1 month", 44640),
	ONE_YEAR("1 year", 535680),
	FIVE_YEAR("5 year", 2678400),
	INFINITE("Infinite", 0);
	
	private String string;
	
	int minutes;
	
	private SensorChartIntervalEnum(String string, int minutes) {
		this.string = string;
		this.minutes = minutes;
	}
	
	@Override
	public String toString() {
		return string;
	}

	boolean isTotalIntervalReached(String firstSampleDate, String secondSampleDate) throws ParseException {
		if (this.equals(INFINITE)) {
			return false;
		}
		else {
			return Date.havePassedMinutesBetweenDates(firstSampleDate, secondSampleDate, minutes+1);
		}
		
	}
	
	public static List<String> getAvailableChartIntervals() {
		List<String> availableChartIntervals = new ArrayList<String>();
		for (SensorChartIntervalEnum chartIntervals : SensorChartIntervalEnum.values()) {
			availableChartIntervals.add(chartIntervals.toString());
		}
		return availableChartIntervals;
	}

	public static SensorChartIntervalEnum getInstance(String totalInterval) {
		SensorChartIntervalEnum totalIntervalEnumRet= null;
		for (SensorChartIntervalEnum totalIntervalEnum : SensorChartIntervalEnum.values()) { 
			if (totalIntervalEnum.toString().equals(totalInterval)) {
				totalIntervalEnumRet = totalIntervalEnum;
			}; 
		}
		return totalIntervalEnumRet;
	}

}
