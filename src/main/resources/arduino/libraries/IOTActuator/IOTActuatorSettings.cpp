/*
 * ActuatorSettings.cpp
 *
 *  Created on: 1 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTActuatorSettings.h"

TimeChangeRule SDT = {"SDT", Last, Sun, Mar, 2, 60};  //Spain Daylight Time UTC + 2 hours
TimeChangeRule SST = {"SST", Last, Sun, Oct, 3, 120};   //Spain Standard Time UTC + 1 hours


IOTActuatorSettings::IOTActuatorSettings(PUBLISH_VALUE_SEC_INTERVAL publishValueInterval, LOCAL_TIMEZONE localTimeZone): localTimeZone() {
	this->publishValueInterval = publishValueInterval;
	TimeChangeRule spainDayLightRule = {"SDT", Last, Sun, Mar, 2, 60};  //Spain Daylight Time UTC + 2 hours
	TimeChangeRule spainStandardTimeRule = {"SST", Last, Sun, Oct, 3, 120};
	this->localTimeZone.setRules(spainStandardTimeRule, spainStandardTimeRule);

}

bool IOTActuatorSettings::isReachedPublishInterval(unsigned long timePassedMillis) {
	if (this->publishValueInterval == NO_PUBLISH_VALUE_INTERVAL) {
		return false;
	}
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= this->publishValueInterval * SECONDS_TO_MSECONDS_FACTOR;
}

long IOTActuatorSettings::toLocalEpochTime(long utcEpochTime) {
	return this->localTimeZone.toLocal(utcEpochTime);
}

