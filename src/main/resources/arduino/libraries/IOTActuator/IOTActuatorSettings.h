/*
 * SensorSettings.h
 *
 *  Created on: 1 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef IOTACTUATORSETTINGS_H_
#define IOTACTUATORSETTINGS_H_

#include <Timezone.h>

enum PUBLISH_VALUE_SEC_INTERVAL {
	NO_PUBLISH_VALUE_INTERVAL = 0,
	PUBLISH_VALUE_20_SEC_INTERVAL = 20,
	PUBLISH_VALUE_1_MINUTE_INTERVAL = 60,
	PUBLISH_VALUE_5_MINUTE_INTERVAL = 300,
	PUBLISH_VALUE_10_MINUTE_INTERVAL = 600,
	PUBLISH_VALUE_20_MINUTE_INTERVAL = 1200,
	PUBLISH_VALUE_30_MINUTE_INTERVAL = 1800,
	PUBLISH_VALUE_1_HOUR_INTERVAL = 3600,
	PUBLISH_VALUE_2_HOUR_INTERVAL = 7200,
	PUBLISH_VALUE_5_HOUR_INTERVAL = 18000,
	PUBLISH_VALUE_10_HOUR_INTERVAL = 36000,
	PUBLISH_VALUE_24_HOUR_INTERVAL = 86400
};



class IOTActuatorSettings {
public:
	IOTActuatorSettings(PUBLISH_VALUE_SEC_INTERVAL publishValueInterval, LOCAL_TIMEZONE localTimeZone);
	bool isReachedPublishInterval(unsigned long timePassedMillis);
	long toLocalEpochTime(long epochTime);
private:
	PUBLISH_VALUE_SEC_INTERVAL publishValueInterval;
	Timezone localTimeZone;
};

#endif /* IOTACTUATORSETTINGS_H_ */
