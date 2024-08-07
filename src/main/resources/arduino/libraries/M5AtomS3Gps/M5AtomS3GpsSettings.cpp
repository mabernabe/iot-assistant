/*
 * M5AtomGpsSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomS3GpsSettings.h"

using namespace m5atoms3gpssettings;

M5AtomS3GpsSettings::M5AtomS3GpsSettings(PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval, LED_ON_PUBLISH_STATE ledActive, LCD_STATE lcdActive)  : ESP32GpsSettings(DEEP_SLEEP_INACTIVE, publishPositionInterval) {
	this->ledActive = ledActive;
	this->lcdActive = lcdActive;
}

M5AtomS3GpsSettings::M5AtomS3GpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval)  : ESP32GpsSettings(deepSleep, publishPositionInterval) {
	this->ledActive = LED_INACTIVE;
	this->lcdActive = LCD_INACTIVE;
}


bool M5AtomS3GpsSettings::isLedActive() {
	return this->ledActive;
}

bool M5AtomS3GpsSettings::isLCDActive() {
	return this->lcdActive;
}

bool M5AtomS3GpsSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_POSITION * SECONDS_TO_MSECONDS_FACTOR;
}



