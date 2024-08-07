/*
 * M5AtomActuatorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMACTUATORSETTINGS_H_
#define M5ATOMACTUATORSETTINGS_H_
#include "ESP32ActuatorSettings.h"

namespace m5atomactuatorsettings {

enum LED_ON_PUBLISH_STATE {
	LED_ACTIVE = true,
	LED_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_VALUE = 2;

class M5AtomActuatorSettings : public ESP32ActuatorSettings {
public:
	M5AtomActuatorSettings(unsigned deepSleepSeconds, PUBLISH_VALUE_SEC_INTERVAL publishValueSecInterval, LED_ON_PUBLISH_STATE ledActive);
	M5AtomActuatorSettings(PUBLISH_VALUE_SEC_INTERVAL publishValueSecInterval, LED_ON_PUBLISH_STATE ledActive);
	bool isLedActive();
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
private:
	bool ledActive;
};

}

#endif /* M5ATOMACTUATORSETTINGS_H_ */
