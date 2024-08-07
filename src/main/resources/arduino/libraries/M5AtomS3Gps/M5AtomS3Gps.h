/*
 * M5AtomS3Gps.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMS3GPS_H_
#define M5ATOMS3GPS_H_
#include "ESP32Gps.h"
#include "M5AtomS3GpsSettings.h"
#include "M5AtomS3.h"

using namespace esp32gps;
using namespace m5atoms3gpssettings;

class M5AtomS3Gps : public ESP32Gps {
public:
	M5AtomS3Gps(M5AtomS3GpsSettings& m5AtomS3GpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient);


private:
	M5AtomS3GpsSettings* m5AtomS3GpsSettings;
    unsigned long ledOnTimeStamp;
    String gpsInterfaceInfo;
    void updateLCDData();
    void postLoop(bool published);
    void turnLedOff();
    void turnLedOn();
    bool ledOn;
};

#endif /* M5ATOMS3GPS_H_ */
