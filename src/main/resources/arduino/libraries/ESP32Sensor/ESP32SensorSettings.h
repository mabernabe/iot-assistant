/*
 * ESP32SensorSettings.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef ESP32SENSORSETTINGS_H_
#define ESP32SENSORSETTINGS_H_
#include "IOTSensorSettings.h"

enum DEEP_SLEEP_STATE {
	DEEP_SLEEP_ACTIVE = true,
	DEEP_SLEEP_INACTIVE = false
};

class ESP32SensorSettings : public IOTSensorSettings{
public:
	ESP32SensorSettings(DEEP_SLEEP_STATE deepSleep, PUBLISH_MEASURE_SEC_INTERVAL publishMeasureInterval);
	bool isDeepSleepEnabled();
	virtual ~ESP32SensorSettings();
private:
	DEEP_SLEEP_STATE deepSleep;
};

#endif /* ESP32SENSORSETTINGS_H_ */
