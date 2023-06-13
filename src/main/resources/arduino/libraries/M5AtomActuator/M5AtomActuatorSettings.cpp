/*
 * M5AtomActuatorSettings.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomActuatorSettings.h"

using namespace m5atomactuatorsettings;

M5AtomActuatorSettings::M5AtomActuatorSettings(unsigned deepSleepSeconds, PUBLISH_VALUE_SEC_INTERVAL publishValueSecInterval, LED_ON_PUBLISH_STATE ledActive)  : ESP32ActuatorSettings(deepSleepSeconds, publishValueSecInterval) {
	this->ledActive = ledActive;
}

M5AtomActuatorSettings::M5AtomActuatorSettings(PUBLISH_VALUE_SEC_INTERVAL publishValueSecInterval, LED_ON_PUBLISH_STATE ledActive)  : ESP32ActuatorSettings(publishValueSecInterval) {
	this->ledActive = ledActive;
}

bool M5AtomActuatorSettings::isLedActive() {
	return this->ledActive;
}

bool M5AtomActuatorSettings::isReachedLedOnInterval(unsigned long timePassedMillis) {
	static int SECONDS_TO_MSECONDS_FACTOR = 1000;
	return timePassedMillis >= TIME_SEC_LED_IS_ON_ON_PUBLISH_VALUE * SECONDS_TO_MSECONDS_FACTOR;
}



