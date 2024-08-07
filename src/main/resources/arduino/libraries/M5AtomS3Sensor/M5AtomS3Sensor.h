/*
 * M5AtomS3Sensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMS3SENSOR_H_
#define M5ATOMS3SENSOR_H_
#include "ESP32Sensor.h"
#include "M5AtomS3SensorSettings.h"
#include "M5AtomS3.h"

using namespace esp32sensor;
using namespace m5atoms3sensorsettings;

class M5AtomS3Sensor : public ESP32Sensor {
public:
	M5AtomS3Sensor(M5AtomS3SensorSettings& m5AtomS3SensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);


private:
	M5AtomS3SensorSettings* m5AtomS3SensorSettings;
    unsigned long ledOnTimeStamp;
    String sensorInterfaceInfo;
    void updateLCDData(Measure measure);
    void postLoop(bool published, Measure measure);
    void turnLedOff();
    void turnLedOn();
    bool ledOn;
};

#endif /* M5ATOMS3SENSOR_H_ */
