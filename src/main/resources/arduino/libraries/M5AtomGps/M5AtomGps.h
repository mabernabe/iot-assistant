/*
 * M5AtomGps.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMGPS_H_
#define M5ATOMGPS_H_
#include "ESP32Gps.h"
#include "M5AtomGpsSettings.h"
#include <M5Atom.h>

using namespace esp32gps;
using namespace m5atomgpssettings;

class M5AtomGps : public ESP32Gps {
public:
	M5AtomGps(M5AtomGpsSettings& m5AtomGpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient);


private:
	M5AtomGpsSettings* m5AtomGpsSettings;
    unsigned long ledOnTimeStamp;
    void postLoop(bool published);
    void turnLedOff();
    void turnLedOn();
    bool ledOn;
};

#endif /* M5ATOMGPS_H_ */
