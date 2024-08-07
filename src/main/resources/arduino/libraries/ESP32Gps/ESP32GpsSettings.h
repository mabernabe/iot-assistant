/*
 * ESP32SensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef ESP32GPSSETTINGS_H_
#define ESP32GPSSETTINGS_H_
#include "IOTGpsSettings.h"

enum DEEP_SLEEP_STATE {
	DEEP_SLEEP_ACTIVE = true,
	DEEP_SLEEP_INACTIVE = false
};

class ESP32GpsSettings : public IOTGpsSettings{
public:
	ESP32GpsSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_POSITION_SEC_INTERVAL publishPositionInterval);
	bool isDeepSleepEnabled();
	virtual ~ESP32GpsSettings();
private:
	DEEP_SLEEP_STATE deepSleep;
};

#endif /* ESP32GPSSETTINGS_H_ */
