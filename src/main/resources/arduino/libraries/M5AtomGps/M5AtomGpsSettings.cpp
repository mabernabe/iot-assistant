/*
 * M5AtomGpsSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomGpsSettings.h"

using namespace m5atomgpssettings;

M5AtomGpsSettings::M5AtomGpsSettings(PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval, LED_ON_PUBLISH_STATE ledEnabled)  : ESP32GpsSettings(DEEP_SLEEP_INACTIVE, publishPositionInterval) {
	this->ledEnabled = ledEnabled;
}

M5AtomGpsSettings::M5AtomGpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval)  : ESP32GpsSettings(deepSleep, publishPositionInterval) {
	this->ledEnabled = LED_INACTIVE;
}


bool M5AtomGpsSettings::isLedEnabled() {
	return this->ledEnabled;
}

bool M5AtomGpsSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_POSITION * SECONDS_TO_MSECONDS_FACTOR;
}



