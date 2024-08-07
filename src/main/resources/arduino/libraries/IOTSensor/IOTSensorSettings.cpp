/*
 * SensorSettings.cpp
 *
 *  Created on: 1 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTSensorSettings.h"


IOTSensorSettings::IOTSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LOCAL_TIMEZONE localTimeZone): localTimeZone() {
	this->publishMeasureInterval = publishMeasureInterval;
	TimeChangeRule spainDayLightRule = {"SDT", Last, Sun, Mar, 2, 60};  //Spain Daylight Time UTC + 2 hours
	TimeChangeRule spainStandardTimeRule = {"SST", Last, Sun, Oct, 3, 120};
	this->localTimeZone.setRules(spainStandardTimeRule, spainStandardTimeRule);
}

bool IOTSensorSettings::isReachedMeasureInterval(unsigned long timePassedMillis) {
	if (this->publishMeasureInterval == NO_PUBLISH_MEASURE_INTERVAL) {
		return false;
	}
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= this->publishMeasureInterval * SECONDS_TO_MSECONDS_FACTOR;
}

PUBLISH_MEASURE_SEC_INTERVAL IOTSensorSettings::getPublishMeasureInterval() {
	return this->publishMeasureInterval;
}

long IOTSensorSettings::toLocalEpochTime(long utcEpochTime) {
	return this->localTimeZone.toLocal(utcEpochTime);
}

