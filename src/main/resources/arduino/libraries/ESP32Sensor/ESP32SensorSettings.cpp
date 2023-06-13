/*
 * ESP32SensorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32SensorSettings.h"

ESP32SensorSettings::ESP32SensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval) : IOTSensorSettings(publishMeasureInterval, SPAIN){
	this->deepSleep = deepSleep;
}


bool ESP32SensorSettings::isDeepSleepEnabled() {
	return this->deepSleep;
}

ESP32SensorSettings::~ESP32SensorSettings() {
}

