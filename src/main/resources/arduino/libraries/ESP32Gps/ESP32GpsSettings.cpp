/*
 * ESP32GpsSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32GpsSettings.h"

ESP32GpsSettings::ESP32GpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval) : IOTGpsSettings(publishPositionInterval, SPAIN){
	this->deepSleep = deepSleep;
}


bool ESP32GpsSettings::isDeepSleepEnabled() {
	return this->deepSleep;
}

ESP32GpsSettings::~ESP32GpsSettings() {
}

