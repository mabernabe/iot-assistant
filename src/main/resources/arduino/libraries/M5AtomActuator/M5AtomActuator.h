/*
 * M5AtomActuator.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef M5ATOMACTUATOR_H_
#define M5ATOMACTUATOR_H_
#include "ESP32Actuator.h"
#include "M5AtomActuatorSettings.h"
#include <M5Atom.h>

using namespace esp32actuator;
using namespace m5atomactuatorsettings;

class M5AtomActuator : public ESP32Actuator {
public:
	M5AtomActuator(M5AtomActuatorSettings& m5AtomActuatorSettings, IOTActuatorPublishInterface& actuatorInterface, UDP* udpClient);


private:
	M5AtomActuatorSettings* m5AtomActuatorSettings;
    unsigned long ledOnTimeStamp;
    void postLoop(bool published, Value value);
    void turnLedOff();
    void turnLedOn();
};

#endif /* M5ATOMACTUATOR_H_ */
