/*
 * M5AtomSensorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomS3SensorSettings.h"

using namespace m5atoms3sensorsettings;

M5AtomS3SensorSettings::M5AtomS3SensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LED_ON_PUBLISH_STATE ledActive, LCD_STATE lcdActive)  : ESP32SensorSettings(DEEP_SLEEP_INACTIVE, publishMeasureInterval) {
	this->ledActive = ledActive;
	this->lcdActive = lcdActive;
}

M5AtomS3SensorSettings::M5AtomS3SensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval)  : ESP32SensorSettings(deepSleep, publishMeasureInterval) {
	this->ledActive = LED_INACTIVE;
	this->lcdActive = LCD_INACTIVE;
}


bool M5AtomS3SensorSettings::isLedActive() {
	return this->ledActive;
}

bool M5AtomS3SensorSettings::isLCDActive() {
	return this->lcdActive;
}

bool M5AtomS3SensorSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE * SECONDS_TO_MSECONDS_FACTOR;
}



