/*
 * M5AtomSensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMSENSORSETTINGS_H_
#define M5ATOMSENSORSETTINGS_H_
#include "ESP32SensorSettings.h"

namespace m5atomsensorsettings {

enum LED_ON_PUBLISH_STATE {
	LED_ACTIVE = true,
	LED_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE = 2;

class M5AtomSensorSettings : public ESP32SensorSettings {
public:
	M5AtomSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LED_ON_PUBLISH_STATE ledEnabled);
	M5AtomSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval);
	bool isLedEnabled();
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
private:
	bool ledEnabled;
};
}

#endif /* M5ATOMSENSORSETTINGS_H_ */
