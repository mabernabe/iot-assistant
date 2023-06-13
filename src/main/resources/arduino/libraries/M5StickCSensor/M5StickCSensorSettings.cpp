/*
 * M5StickCSensorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5StickCSensorSettings.h"

using namespace m5stickcsensorsettings;

M5StickCSensorSettings::M5StickCSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LCD_STATE lcdActive)  : ESP32SensorSettings(DEEP_SLEEP_INACTIVE, publishMeasureInterval) {
	this->lcdActive = lcdActive;
}

M5StickCSensorSettings::M5StickCSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval)  : ESP32SensorSettings(deepSleep, publishMeasureInterval) {
	this->lcdActive = LCD_INACTIVE;
}


bool M5StickCSensorSettings::isLCDActive() {
	return this->lcdActive;
}

bool M5StickCSensorSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE * SECONDS_TO_MSECONDS_FACTOR;
}



