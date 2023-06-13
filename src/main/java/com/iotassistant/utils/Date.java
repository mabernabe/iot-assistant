package com.iotassistant.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Date {
	
	public static final String APPLICATION_DATES_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCurrentDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getApplicationDatesFormat());
	    LocalDateTime currentDate = LocalDateTime.now();
	    return formatter.format(currentDate);  
	}
	
	public static final String getApplicationDatesFormat() {
		return APPLICATION_DATES_FORMAT;
	}

	public static boolean havePassedMinutesBetweenDates(String startDate, String endDate, int minutes) throws ParseException {
		boolean hasElapsedTime = false;
		long minutesDiff = getDatesDifference(startDate, endDate, TimeUnit.MINUTES);
		if (minutesDiff >= minutes) {
			hasElapsedTime = true;
		}
		return hasElapsedTime;
	}
	
	private static long getDatesDifference(String startDate, String endDate, TimeUnit timeUnit) throws ParseException {
		java.util.Date javaStartDate = new SimpleDateFormat(getApplicationDatesFormat()).parse(startDate); 
		java.util.Date javaEndDate = new SimpleDateFormat(getApplicationDatesFormat()).parse(endDate);
	    long diffInMillies = javaEndDate.getTime() - javaStartDate.getTime();
	    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public static boolean isValidDate(String date) {
		try {
			new SimpleDateFormat(getApplicationDatesFormat()).parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		} 
	}

}
