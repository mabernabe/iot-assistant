/*
 * M5StickCPlusSensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5STICKCPLUSSENSOR_H_
#define M5STICKCPLUSSENSOR_H_
#include "ESP32Sensor.h"
#include "M5StickCPlusSensorSettings.h"
#include "M5StickCPlus.h"

using namespace esp32sensor;
using namespace m5stickcplussensorsettings;

class M5StickCPlusSensor : public ESP32Sensor {
public:
	M5StickCPlusSensor(M5StickCPlusSensorSettings& m5StickCPlusSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);
	void begin();

private:
	M5StickCPlusSensorSettings* m5StickCPlusSensorSettings;
    unsigned long ledOnTimeStamp;
    String sensorInterfaceInfo;
    void updateLCDData(Measure measure);
    void postLoop(bool published, Measure measure);
    void deepSleep();
};

#endif /* M5STICKCPLUSSENSOR_H_ */
