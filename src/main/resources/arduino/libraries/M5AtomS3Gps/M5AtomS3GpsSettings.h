/*
 * M5AtomGpsSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMS3GPSSETTINGS_H_
#define M5ATOMS3GPSSETTINGS_H_
#include "ESP32GpsSettings.h"

namespace m5atoms3gpssettings {

enum LED_ON_PUBLISH_STATE {
	LED_ACTIVE = true,
	LED_INACTIVE = false
};

enum LCD_STATE {
	LCD_ACTIVE = true,
	LCD_INACTIVE = false
};

static unsigned long TIME_SEC_LED_IS_ON_ON_PUBLISH_POSITION = 2;

class M5AtomS3GpsSettings : public ESP32GpsSettings {
public:
	M5AtomS3GpsSettings(PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval, LED_ON_PUBLISH_STATE ledActive, LCD_STATE lcdActive);
	M5AtomS3GpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval);
	bool isLedActive();
	bool isReachedLedOnInterval(unsigned long timePassedMillis);
	bool isLCDActive();
private:
	bool ledActive;
	bool lcdActive;

};
}

#endif /* M5ATOMS3GPSSETTINGS_H_ */
