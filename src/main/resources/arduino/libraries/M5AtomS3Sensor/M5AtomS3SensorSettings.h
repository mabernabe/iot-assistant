/*
 * M5AtomSensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMS3SENSORSETTINGS_H_
#define M5ATOMS3SENSORSETTINGS_H_
#include "ESP32SensorSettings.h"

namespace m5atoms3sensorsettings {

enum LED_ON_PUBLISH_STATE {
	LED_ACTIVE = true,
	LED_INACTIVE = false
};

enum LCD_STATE {
	LCD_ACTIVE = true,
	LCD_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_MEASURE = 2;

class M5AtomS3SensorSettings : public ESP32SensorSettings {
public:
	M5AtomS3SensorSettings(PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval, LED_ON_PUBLISH_STATE ledActive, LCD_STATE lcdActive);
	M5AtomS3SensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval);
	bool isLedActive();
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
	bool isLCDActive();
private:
	bool ledActive;
	bool lcdActive;

};
}

#endif /* M5ATOMS3SENSORSETTINGS_H_ */
