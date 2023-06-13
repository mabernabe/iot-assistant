/*
 * ESP32ActuatorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32ActuatorSettings.h"

ESP32ActuatorSettings::ESP32ActuatorSettings(unsigned deepSleepSeconds, PUBLISH_VALUE_SEC_INTERVAL publishValueInterval) : IOTActuatorSettings(publishValueInterval, SPAIN){
	this->deepSleepSeconds = deepSleepSeconds;
}

ESP32ActuatorSettings::ESP32ActuatorSettings(PUBLISH_VALUE_SEC_INTERVAL publishValueInterval) : IOTActuatorSettings(publishValueInterval, SPAIN){
	this->deepSleepSeconds = 0;
}

unsigned ESP32ActuatorSettings::getDeepSleepSeconds() {
	return this->deepSleepSeconds;
}

ESP32ActuatorSettings::~ESP32ActuatorSettings() {
}

