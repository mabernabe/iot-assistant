/*
 * IOTSensor.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTSensor.h"

IOTSensor::IOTSensor(IOTSensorSettings &sensorSettings, IOTSensorPublishInterface &sensorInterface, UDP* udpClient): timeClient(*udpClient, "0.es.pool.ntp.org", 0, 60000, 1337) {
	this->sensorInterface = &sensorInterface;
	this->sensorSettings = &sensorSettings;
	this->firstPublication = true;
	this->lastPublishTimeStamp = 0;
	callbackAnalogMeasuresCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_MEASURED; i++) {
		lastAnalogPublishedValues[i] = "";
	}
	callbackDigitalMeasuresCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_MEASURED; i++) {
		lastDigitalPublishedValues[i] = false;
	}
}

IOTSensor::~IOTSensor() {
}


DateTime IOTSensor::getCurrentDate() {
	timeClient.update();
	unsigned long utcEpochTime = timeClient.getEpochTime();
	unsigned long currentEpochTime = sensorSettings->toLocalEpochTime(utcEpochTime);
	DateTime currentDate;
	currentDate.setEpochTime(currentEpochTime);
	return currentDate;
}

bool IOTSensor::loop(void) {
	this->sensorInterface->loop();
	if (!timeClient.update()) {
		return false;
	}
	bool publishedMeasure = false;
	Measure measure = getMeasure();
	if(measure.getNumberOfMeasureValues() > 0) {
		if (this->firstPublication || this->reachedMeasureInterval() || this->shouldPublishMeasureChange(measure)) {
			publishedMeasure = this->sensorInterface->publishMeasure(measure);
		}
		if (publishedMeasure) {
			lastPublishTimeStamp = millis();
			this->updateLastPublishedValues(measure);
			this->firstPublication = false;
		}
	}
	postLoop(publishedMeasure, measure);
	return publishedMeasure;
}

bool IOTSensor::connected(void) {
	return this->sensorInterface->connected();
}

void IOTSensor::disconnect(void) {
	if (this->connected()) {
		this->sensorInterface->disconnect();
	}
}

Measure IOTSensor::getMeasure() {
	DateTime currentDate = getCurrentDate() ;
	Measure measure(currentDate);
	this->addAnalogCallbackMeasures(&measure);
	this->addDigitalCallbackMeasures(&measure);
	return measure;
}

void IOTSensor::addAnalogCallbackMeasures(Measure* measure) {
	for (int i = 0; i < callbackAnalogMeasuresCount; i++) {
		AnalogPropertyMeasured propertyMeasured = callbackAnalogMeasuresSettings[i].propertyMeasured;
		String value = callbackAnalogMeasuresSettings[i].getMeasureCallback(propertyMeasured);
		measure->addMeasureValue(propertyMeasured, value);
	}
}

void IOTSensor::addDigitalCallbackMeasures(Measure* measure) {
	for (int i = 0; i < callbackDigitalMeasuresCount; i++) {
		DigitalPropertyMeasured propertyMeasured = callbackDigitalMeasuresSettings[i].propertyMeasured;
		bool value = callbackDigitalMeasuresSettings[i].getMeasureCallback(propertyMeasured);
		measure->addMeasureValue(propertyMeasured, value);
	}
}

bool IOTSensor::reachedMeasureInterval() {
	return this->sensorSettings->isReachedMeasureInterval(millis() - lastPublishTimeStamp);
}

bool IOTSensor::shouldPublishMeasureChange(Measure measure) {
	if (this->shouldPublishAnalogMeasureChange(measure) || this->shouldPublishDigitalMeasureChange(measure)) {
		return true;
	}
	return false;
}

bool IOTSensor::shouldPublishAnalogMeasureChange(Measure measure) {
	bool shouldPublishMeasureChange = false;
	for(int i = 0; i < callbackAnalogMeasuresCount; i++) {
		AnalogPropertyMeasured propertyMeasured = callbackAnalogMeasuresSettings[i].propertyMeasured;
		String lastValue = lastAnalogPublishedValues[i];
		if (callbackAnalogMeasuresSettings[i].shouldPublishChangeCallback(propertyMeasured, lastValue, measure.getMeasureValue(propertyMeasured))) {
			shouldPublishMeasureChange = true;
		}
	}
	return shouldPublishMeasureChange;
}

bool IOTSensor::shouldPublishDigitalMeasureChange(Measure measure) {
	bool shouldPublishMeasureChange = false;
	for(int i = 0; i < callbackDigitalMeasuresCount; i++) {
		DigitalPropertyMeasured propertyMeasured = callbackDigitalMeasuresSettings[i].propertyMeasured;
		bool lastValue = lastDigitalPublishedValues[i];
		if (callbackDigitalMeasuresSettings[i].shouldPublishChangeCallback(propertyMeasured, lastValue, measure.getMeasureValue(propertyMeasured))) {
			shouldPublishMeasureChange = true;
		}
	}
	return shouldPublishMeasureChange;
}

void IOTSensor::updateLastPublishedValues(Measure measure) {
	this->updateLastAnalogPublishedValues(measure);
	this->updateLastDigitalPublishedValues(measure);
}

void IOTSensor::updateLastAnalogPublishedValues(Measure measure) {
	for (int i = 0; i < callbackAnalogMeasuresCount; i++) {
		AnalogPropertyMeasured propertyMeasured = callbackAnalogMeasuresSettings[i].propertyMeasured;
		lastAnalogPublishedValues[i] = measure.getMeasureValue(propertyMeasured);
	}
}

void IOTSensor::updateLastDigitalPublishedValues(Measure measure) {
	for (int i = 0; i < callbackDigitalMeasuresCount; i++) {
		DigitalPropertyMeasured propertyMeasured = callbackDigitalMeasuresSettings[i].propertyMeasured;
		lastDigitalPublishedValues[i] = measure.getMeasureValue(propertyMeasured);
	}
}

void IOTSensor::addCallbackMeasureSettings(CallbackAnalogMeasureSetting callbackMeasureSettings) {
	callbackAnalogMeasuresSettings[callbackAnalogMeasuresCount] = callbackMeasureSettings;
	callbackAnalogMeasuresCount++;
}

void IOTSensor::addCallbackMeasureSettings(CallbackDigitalMeasureSetting callbackMeasureSettings) {
	callbackDigitalMeasuresSettings[callbackDigitalMeasuresCount] = callbackMeasureSettings;
	callbackDigitalMeasuresCount++;
}

String IOTSensor::getSensorInterfaceInfo() {
	return this->sensorInterface->getInfo();
}

