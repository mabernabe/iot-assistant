/*
 * M5AtomGps.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomGps.h"
#include "Datetime.h"


M5AtomGps::M5AtomGps(M5AtomGpsSettings& m5AtomGpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient) : ESP32Gps(m5AtomGpsSettings, gpsInterface, udpClient) {
	M5.begin(true, false, true);
	this->m5AtomGpsSettings = &m5AtomGpsSettings;
	this->ledOnTimeStamp = 0;
	this->ledOn = false;

}

void M5AtomGps::postLoop(bool published) {
	ESP32Gps::postLoop(published);
	M5.update();
	if (published && m5AtomGpsSettings->isLedEnabled()) {
		turnLedOn();
		ledOnTimeStamp = millis();
	}
	if (this->ledOn) {
		unsigned long LedOnElapsedTime = millis() - ledOnTimeStamp ;
		if (m5AtomGpsSettings->isReachedLedOnInterval(LedOnElapsedTime)) {
			turnLedOff();
		}
	}
}


void M5AtomGps::turnLedOn() {
	M5.dis.drawpix(0, CRGB::White); //Turn on the led
	this->ledOn = true;
	delay(100);
}

void M5AtomGps::turnLedOff() {
	M5.dis.drawpix(0, 0);
	this->ledOn = false;
	delay(100);
}

