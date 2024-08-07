/*
 * ESP32Sensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef ESP32GPS_H_
#define ESP32GPS_H_
#include "ESP32GpsSettings.h"
#include "IOTGps.h"

using namespace position;

namespace esp32gps {



class ESP32Gps : public IOTGps {
public:
	ESP32Gps(ESP32GpsSettings& esp32GpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient);

protected:
	void postLoop(bool published);
	void deepSleep();

private:
	ESP32GpsSettings* esp32GpsSettings;

};

}

#endif /* ESP32GPS_H_ */
