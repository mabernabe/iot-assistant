/*
 * M5AtomGpsSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMGPSSETTINGS_H_
#define M5ATOMGPSSETTINGS_H_
#include "ESP32GpsSettings.h"

namespace m5atomgpssettings {

enum LED_ON_PUBLISH_STATE {
	LED_ACTIVE = true,
	LED_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_POSITION = 2;

class M5AtomGpsSettings : public ESP32GpsSettings {
public:
	M5AtomGpsSettings(PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval, LED_ON_PUBLISH_STATE ledEnabled);
	M5AtomGpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval);
	bool isLedEnabled();
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
private:
	bool ledEnabled;
};
}

#endif /* M5ATOMGPSSETTINGS_H_ */
