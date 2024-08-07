/*
 * M5AtomSensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5STICKCSENSORSETTINGS_H_
#define M5STICKCSENSORSETTINGS_H_
#include "ESP32SensorSettings.h"

namespace m5stickcsensorsettings {


enum LCD_STATE {
	LCD_ACTIVE = true,
	LCD_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE = 2;

class M5StickCSensorSettings : public ESP32SensorSettings {
public:
	M5StickCSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LCD_STATE lcdActive);
	M5StickCSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval);
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
	bool isLCDActive();
private:
	bool lcdActive;
};
}

#endif /* M5STICKCSENSORSETTINGS_H_ */
