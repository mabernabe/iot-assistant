/*
 * M5AtomGps.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomS3Gps.h"
#include "Datetime.h"


M5AtomS3Gps::M5AtomS3Gps(M5AtomS3GpsSettings& m5AtomS3GpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient) : ESP32Gps(m5AtomS3GpsSettings,gpsInterface, udpClient) {
	 M5.begin(m5AtomS3GpsSettings.isLCDActive() ,  true, true, m5AtomS3GpsSettings.isLedActive());
     this->m5AtomS3GpsSettings = &m5AtomS3GpsSettings;
	 this->ledOnTimeStamp = 0;
	 this->gpsInterfaceInfo = String("");
	 this->ledOn = false;

}

void M5AtomS3Gps::postLoop(bool published) {
	ESP32Gps::postLoop(published);
	if (m5AtomS3GpsSettings->isLCDActive() && !this->gpsInterfaceInfo.equals(this->getGpsInterfaceInfo()) ) {
		this->updateLCDData();
	}
	if (published && m5AtomS3GpsSettings->isLedActive()) {
		turnLedOn();
		ledOnTimeStamp = millis();
	}
	if (this->ledOn) {
		unsigned long LedOnElapsedTime = millis() - ledOnTimeStamp ;
		if (m5AtomS3GpsSettings->isReachedLedOnInterval(LedOnElapsedTime)) {
			turnLedOff();
		}
	}
}

void M5AtomS3Gps::updateLCDData() {
	this->gpsInterfaceInfo = this->getGpsInterfaceInfo();
	M5.Lcd.println("SENSOR");
	M5.Lcd.println();
	M5.Lcd.print(this->gpsInterfaceInfo);
	M5.Lcd.println();
}

void M5AtomS3Gps::turnLedOn() {
	M5.dis.drawpix(0x00ff00);
	this->ledOn = true;
	M5.dis.show();

}

void M5AtomS3Gps::turnLedOff() {
	M5.dis.clear();
	this->ledOn = false;
}

