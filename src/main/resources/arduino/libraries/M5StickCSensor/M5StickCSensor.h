/*
 * M5StickCSensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5STICKCSENSOR_H_
#define M5STICKCSENSOR_H_
#include "ESP32Sensor.h"
#include "M5StickCSensorSettings.h"
#include "M5StickC.h"

using namespace esp32sensor;
using namespace m5stickcsensorsettings;

class M5StickCSensor : public ESP32Sensor {
public:
	M5StickCSensor(M5StickCSensorSettings& m5StickCSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);
	void begin();

private:
	M5StickCSensorSettings* m5StickCSensorSettings;
    unsigned long ledOnTimeStamp;
    String sensorInterfaceInfo;
    void updateLCDData(Measure measure);
    void postLoop(bool published, Measure measure);
    void deepSleep();
};

#endif /* M5STICKCSENSOR_H_ */
