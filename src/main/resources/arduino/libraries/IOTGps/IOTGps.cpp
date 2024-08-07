/*
 * IOTGps.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTGps.h"

IOTGps::IOTGps(IOTGpsSettings& gpsSettings, IOTGpsPublishInterface& gpsInterface, UDP* udpClient): timeClient(*udpClient, "0.es.pool.ntp.org", 0, 60000, 1337) {
	this->gpsInterface = &gpsInterface;
	this->gpsSettings = &gpsSettings;
	this->isFirstPublish = true;
	this->lastPublishTimeStamp = 0;
	this->isPositionReadyCallback = []() -> bool {return true;};
}

IOTGps::~IOTGps() {
}

DateTime IOTGps::getCurrentDate() {
	timeClient.update();
	unsigned long utcEpochTime = timeClient.getEpochTime();
	unsigned long currentEpochTime = gpsSettings->toLocalEpochTime(utcEpochTime);
	DateTime currentDate;
	currentDate.setEpochTime(currentEpochTime);
	return currentDate;
}

bool IOTGps::loop(void) {
	this->gpsInterface->loop();
	if (!timeClient.update()) {
		return false;
	}
	bool publishedPosition = false;
	Position position = getPosition();
	if (this->isPositionReadyCallback() && (this->isFirstPublish || this->reachedPublishInterval())) {
		publishedPosition = this->gpsInterface->publishPosition(position);
	}
	if (publishedPosition) {
		lastPublishTimeStamp = millis();
		this->isFirstPublish = false;
	}
	postLoop(publishedPosition);
	return publishedPosition;
}

bool IOTGps::connected(void) {
	return this->gpsInterface->connected();
}

void IOTGps::disconnect(void) {
	if (this->connected()) {
		this->gpsInterface->disconnect();
	}
}

Position IOTGps::getPosition() {
	DateTime currentDate = getCurrentDate() ;
	Position position(currentDate);
	position.setLongitude(longitudeCallback());
	position.setLatitude(latitudeCallback());
	return position;
}


bool IOTGps::reachedPublishInterval() {
	return this->gpsSettings->isReachedPublishInterval(millis() - lastPublishTimeStamp);
}

void IOTGps::setLatitudeCallback(String (*latitudeCallback)()) {
	this->latitudeCallback = latitudeCallback;
}

void IOTGps::setLongitudeCallback(String (*longitudeCallback)()) {
	this->longitudeCallback = longitudeCallback;
}

void IOTGps::setIsPositionReadyCallback(bool (*isPositionReadyCallback)()) {
	this->isPositionReadyCallback = isPositionReadyCallback;
}

String IOTGps::getGpsInterfaceInfo() {
	return this->gpsInterface->getInfo();
}



