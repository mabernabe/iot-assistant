/*
 * M5AtomSensor.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomSensor.h"
#include "Datetime.h"


M5AtomSensor::M5AtomSensor(M5AtomSensorSettings& m5AtomSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient) : ESP32Sensor(m5AtomSensorSettings,sensorInterface, udpClient) {
	M5.begin(true, false, true);
	this->m5AtomSensorSettings = &m5AtomSensorSettings;
	this->ledOnTimeStamp = 0;
	this->ledOn = false;

}

void M5AtomSensor::postLoop(bool published, Measure measure) {
	ESP32Sensor::postLoop(published, measure);
	M5.update();
	if (published && m5AtomSensorSettings->isLedEnabled()) {
		turnLedOn();
		ledOnTimeStamp = millis();
	}
	if (this->ledOn) {
		unsigned long LedOnElapsedTime = millis() - ledOnTimeStamp ;
		if (m5AtomSensorSettings->isReachedLedOnInterval(LedOnElapsedTime)) {
			turnLedOff();
		}
	}
}


void M5AtomSensor::turnLedOn() {
	M5.dis.drawpix(0, CRGB::White); //Turn on the led
	this->ledOn = true;
	delay(100);
}

void M5AtomSensor::turnLedOff() {
	M5.dis.drawpix(0, 0);
	this->ledOn = false;
	delay(100);
}

