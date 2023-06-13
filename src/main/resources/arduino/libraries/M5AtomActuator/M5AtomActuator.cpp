/*
 * M5AtomActuator.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomActuator.h"
#include "Datetime.h"


M5AtomActuator::M5AtomActuator(M5AtomActuatorSettings& m5AtomActuatorSettings, IOTActuatorPublishInterface& actuatorInterface, UDP* udpClient) : ESP32Actuator(m5AtomActuatorSettings, actuatorInterface, udpClient) {
	M5.begin(true, false, true);
	this->m5AtomActuatorSettings = &m5AtomActuatorSettings;
	this->ledOnTimeStamp = 0;

}

void M5AtomActuator::postLoop(bool published, Value value) {
	if (published && m5AtomActuatorSettings->isLedActive()) {
		turnLedOn();
		ledOnTimeStamp = millis();
	}
	if (!published && m5AtomActuatorSettings->isLedActive()) {
		unsigned long LedOnElapsedTime = millis() - ledOnTimeStamp ;
		if (m5AtomActuatorSettings->isReachedLedOnInterval(LedOnElapsedTime)) {
			turnLedOff();
		}
	}
}


void M5AtomActuator::turnLedOn() {
	M5.dis.drawpix(0, CRGB::White); //Turn on the led
}

void M5AtomActuator::turnLedOff() {
	M5.dis.drawpix(0, 0);
}

