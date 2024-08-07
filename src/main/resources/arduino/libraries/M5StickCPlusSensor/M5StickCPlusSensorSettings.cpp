/*
 * M5AtomSensorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5StickCPlusSensorSettings.h"

using namespace m5stickcplussensorsettings;

M5StickCPlusSensorSettings::M5StickCPlusSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LCD_STATE lcdActive)  : ESP32SensorSettings(DEEP_SLEEP_INACTIVE, publishMeasureInterval) {
	this->lcdActive = lcdActive;
}

M5StickCPlusSensorSettings::M5StickCPlusSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval)  : ESP32SensorSettings(deepSleep, publishMeasureInterval) {
	this->lcdActive = LCD_INACTIVE;
}


bool M5StickCPlusSensorSettings::isLCDActive() {
	return this->lcdActive;
}

bool M5StickCPlusSensorSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE * SECONDS_TO_MSECONDS_FACTOR;
}



