/*
 * SensorSettings.cpp
 *
 *  Created on: 1 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTGpsSettings.h"


IOTGpsSettings::IOTGpsSettings(PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval, LOCAL_TIMEZONE localTimeZone): localTimeZone() {
	this->publishPositionInterval = publishPositionInterval;
	TimeChangeRule spainDayLightRule = {"SDT", Last, Sun, Mar, 2, 60};  //Spain Daylight Time UTC + 2 hours
	TimeChangeRule spainStandardTimeRule = {"SST", Last, Sun, Oct, 3, 120};
	this->localTimeZone.setRules(spainStandardTimeRule, spainStandardTimeRule);
}

bool IOTGpsSettings::isReachedPublishInterval(unsigned long timePassedMillis) {
	if (this->publishPositionInterval == NO_PUBLISH_POSITION_INTERVAL) {
		return false;
	}
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= this->publishPositionInterval * SECONDS_TO_MSECONDS_FACTOR;
}

PUBLISH_POSITION_SEC_INTERVAL IOTGpsSettings::getPublishPositionInterval() {
	return this->publishPositionInterval;
}

long IOTGpsSettings::toLocalEpochTime(long utcEpochTime) {
	return this->localTimeZone.toLocal(utcEpochTime);
}

