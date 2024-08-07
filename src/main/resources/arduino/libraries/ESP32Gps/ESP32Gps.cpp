/*
 * ESP32Gps.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32Gps.h"

using namespace esp32gps;



ESP32Gps::ESP32Gps(ESP32GpsSettings& esp32GpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient):  IOTGps(esp32GpsSettings, gpsInterface, udpClient){
	this->esp32GpsSettings = &esp32GpsSettings;
}


void ESP32Gps::postLoop(bool published) {
	if (this->esp32GpsSettings->isDeepSleepEnabled())  {
		this->deepSleep();
	}
}

void ESP32Gps::deepSleep() {
	static int SEC_TO_US_FACTOR  = 1000000;
	static int WAKE_UP_DELAY_SEC  = 3; // Time esp32 takes to wake up, connect network, publish measure...
	unsigned long publishInterval = this->esp32GpsSettings->getPublishPositionInterval() * SEC_TO_US_FACTOR;
	esp_sleep_enable_timer_wakeup(publishInterval - WAKE_UP_DELAY_SEC * SEC_TO_US_FACTOR);
	Serial.println("Going to sleep");
	this->disconnect();
	while (this->connected()) {
		delay(100);
	}
	delay(1000);  // Wait 1 second for the network buffer to get flushed
	esp_deep_sleep_start();
}



