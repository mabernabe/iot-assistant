/*
 * M5AtomSensorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomSensorSettings.h"

using namespace m5atomsensorsettings;

M5AtomSensorSettings::M5AtomSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LED_ON_PUBLISH_STATE ledEnabled)  : ESP32SensorSettings(DEEP_SLEEP_INACTIVE, publishMeasureInterval) {
	this->ledEnabled = ledEnabled;
}

M5AtomSensorSettings::M5AtomSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval)  : ESP32SensorSettings(deepSleep, publishMeasureInterval) {
	this->ledEnabled = LED_INACTIVE;
}


bool M5AtomSensorSettings::isLedEnabled() {
	return this->ledEnabled;
}

bool M5AtomSensorSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE * SECONDS_TO_MSECONDS_FACTOR;
}



