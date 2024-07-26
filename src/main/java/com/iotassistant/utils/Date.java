package com.iotassistant.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Date {
	
	private static final String APPLICATION_DATES_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static String getCurrentDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(APPLICATION_DATES_FORMAT);
	    LocalDateTime currentDate = LocalDateTime.now();
	    return formatter.format(currentDate);  
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
		java.util.Date javaStartDate = new SimpleDateFormat(APPLICATION_DATES_FORMAT).parse(startDate); 
		java.util.Date javaEndDate = new SimpleDateFormat(APPLICATION_DATES_FORMAT).parse(endDate);
	    long diffInMillies = javaEndDate.getTime() - javaStartDate.getTime();
	    return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public static boolean isValidDate(String date) {
		try {
			new SimpleDateFormat(APPLICATION_DATES_FORMAT).parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		} 
	}


	public static String getTimeFromUptime(long uptime) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	    return uptime / (3600 * 1000 * 24) + ":" + dateFormat.format(uptime);
	}

}
