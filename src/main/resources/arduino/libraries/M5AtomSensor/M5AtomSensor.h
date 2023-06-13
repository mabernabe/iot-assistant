/*
 * M5AtomSensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMSENSOR_H_
#define M5ATOMSENSOR_H_
#include "ESP32Sensor.h"
#include "M5AtomSensorSettings.h"
#include <M5Atom.h>

using namespace esp32sensor;
using namespace m5atomsensorsettings;

class M5AtomSensor : public ESP32Sensor {
public:
	M5AtomSensor(M5AtomSensorSettings& m5AtomSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);


private:
	M5AtomSensorSettings* m5AtomSensorSettings;
    unsigned long ledOnTimeStamp;
    void postLoop(bool published, Measure measure);
    void turnLedOff();
    void turnLedOn();
    bool ledOn;
};

#endif /* M5ATOMSENSOR_H_ */
