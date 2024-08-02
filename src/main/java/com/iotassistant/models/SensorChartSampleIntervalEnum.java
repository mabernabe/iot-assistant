package com.iotassistant.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.iotassistant.utils.Date;

public enum SensorChartSampleIntervalEnum {
	ONE_MINUTE("1 minute", 1),
	FIVE_MINUTES("5 minute", 5),
	HALF_HOUR("Half hour", 30),
	ONE_HOUR("1 hour", 60),
	FIVE_HOURS("5 hours", 300),
	EIGHT_HOURS("8 hours", 480),
	ONE_DAY("1 day", 1440),
	TWO_DAYS("2 days", 2880),
	ONE_WEEK("1 week", 10080),
	ONE_MONTH("1 month", 43200);

	String string; 
	
	int minutes;
	
	SensorChartSampleIntervalEnum(String string, int minutes) {
		this.string = string;
		this.minutes = minutes;
	}
	
	public String toString() {
		return string;
	}
	
	boolean isSampleIntervalReached(String firstSampleDate, String secondSampleDate) throws ParseException {
		return Date.havePassedMinutesBetweenDates(firstSampleDate, secondSampleDate, minutes);
		
	}

	public static List<String> getAvailableSampleIntervals() {
		List<String> availableSampleIntervals = new ArrayList<String>();
		for (SensorChartSampleIntervalEnum  sampleIntervals : SensorChartSampleIntervalEnum .values()) {
			availableSampleIntervals.add(sampleIntervals.toString());
		}
		return availableSampleIntervals;
	}

	public static SensorChartSampleIntervalEnum getInstance(String sampleInterval) {
		SensorChartSampleIntervalEnum sampleIntervalEnumRet= null;
		for (SensorChartSampleIntervalEnum sampleIntervalEnum : SensorChartSampleIntervalEnum.values()) { 
			if (sampleIntervalEnum.toString().equals(sampleInterval)) {
				sampleIntervalEnumRet = sampleIntervalEnum;
			}; 
		}
		return sampleIntervalEnumRet;
	}
	
	
}
