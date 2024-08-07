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
	this->isFirstPublish = true;
	this->lastPublishTimeStamp = 0;
	analogMeasureSettingsCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_MEASURED; i++) {
		analogPublishedValues[i] = "";
	}
	digitalMeasureSettingsCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_MEASURED; i++) {
		digitalPublishedValues[i] = false;
	}
	this->isMeasureReadyCallback = []() -> bool {return true;};
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
		if (this->isFirstPublish || this->reachedMeasureInterval() || this->shouldPublishMeasureChange(measure)) {
			publishedMeasure = this->sensorInterface->publishMeasure(measure);
		}
		if (publishedMeasure) {
			lastPublishTimeStamp = millis();
			this->updatePublishedValues(measure);
			this->isFirstPublish = false;
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
	this->addAnalogValue(&measure);
	this->addDigitalValue(&measure);
	return measure;
}

void IOTSensor::addAnalogValue(Measure* measure) {
	for (int i = 0; i < analogMeasureSettingsCount; i++) {
		AnalogPropertyMeasured propertyMeasured = analogMeasureSettings[i].propertyMeasured;
		String value = analogMeasureSettings[i].getMeasure(propertyMeasured);
		measure->addMeasureValue(propertyMeasured, value);
	}
}

void IOTSensor::addDigitalValue(Measure* measure) {
	for (int i = 0; i < digitalMeasureSettingsCount; i++) {
		DigitalPropertyMeasured propertyMeasured = digitalMeasureSettings[i].propertyMeasured;
		bool value = digitalMeasureSettings[i].getMeasure(propertyMeasured);
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
	for(int i = 0; i < analogMeasureSettingsCount; i++) {
		AnalogPropertyMeasured propertyMeasured = analogMeasureSettings[i].propertyMeasured;
		String lastValue = analogPublishedValues[i];
		if (analogMeasureSettings[i].shouldPublishChange(propertyMeasured, lastValue, measure.getMeasureValue(propertyMeasured))) {
			shouldPublishMeasureChange = true;
		}
	}
	return shouldPublishMeasureChange;
}

bool IOTSensor::shouldPublishDigitalMeasureChange(Measure measure) {
	bool shouldPublishMeasureChange = false;
	for(int i = 0; i < digitalMeasureSettingsCount; i++) {
		DigitalPropertyMeasured propertyMeasured = digitalMeasureSettings[i].propertyMeasured;
		bool lastValue = digitalPublishedValues[i];
		if (digitalMeasureSettings[i].shouldPublishChange(propertyMeasured, lastValue, measure.getMeasureValue(propertyMeasured))) {
			shouldPublishMeasureChange = true;
		}
	}
	return shouldPublishMeasureChange;
}

void IOTSensor::updatePublishedValues(Measure measure) {
	this->updateAnalogPublishedValues(measure);
	this->updateDigitalPublishedValues(measure);
}

void IOTSensor::updateAnalogPublishedValues(Measure measure) {
	for (int i = 0; i < analogMeasureSettingsCount; i++) {
		AnalogPropertyMeasured propertyMeasured = analogMeasureSettings[i].propertyMeasured;
		analogPublishedValues[i] = measure.getMeasureValue(propertyMeasured);
	}
}

void IOTSensor::updateDigitalPublishedValues(Measure measure) {
	for (int i = 0; i < digitalMeasureSettingsCount; i++) {
		DigitalPropertyMeasured propertyMeasured = digitalMeasureSettings[i].propertyMeasured;
		digitalPublishedValues[i] = measure.getMeasureValue(propertyMeasured);
	}
}

void IOTSensor::addMeasureSetting(AnalogMeasureSetting measureSetting) {
	analogMeasureSettings[analogMeasureSettingsCount] = measureSetting;
	analogMeasureSettingsCount++;
}

void IOTSensor::addMeasureSetting(DigitalMeasureSetting measureSetting) {
	digitalMeasureSettings[digitalMeasureSettingsCount] = measureSetting;
	digitalMeasureSettingsCount++;
}

void IOTSensor::setIsMeasureReadyCallback(bool (*isMeasureReadyCallback)()) {
	this->isMeasureReadyCallback = isMeasureReadyCallback;
}

String IOTSensor::getSensorInterfaceInfo() {
	return this->sensorInterface->getInfo();
}

