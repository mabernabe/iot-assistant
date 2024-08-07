/*
 * M5AtomSensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5STICKCPLUSSENSORSETTINGS_H_
#define M5STICKCPLUSSENSORSETTINGS_H_
#include "ESP32SensorSettings.h"

namespace m5stickcplussensorsettings {


enum LCD_STATE {
	LCD_ACTIVE = true,
	LCD_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE = 2;

class M5StickCPlusSensorSettings : public ESP32SensorSettings {
public:
	M5StickCPlusSensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LCD_STATE lcdActive);
	M5StickCPlusSensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval);
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
	bool isLCDActive();
private:
	bool lcdActive;
};
}

#endif /* M5STICKCPLUSSENSORSETTINGS_H_ */
