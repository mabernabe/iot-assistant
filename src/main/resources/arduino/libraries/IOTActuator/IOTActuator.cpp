/*
 * IOTSensor.cpp
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "IOTActuator.h"

CallbackAnalogValueSetting callbackAnalogValuesSettings [MAX_PROPERTIES_ACTUATED];
int callbackAnalogValuesCount;
CallbackDigitalValueSetting callbackDigitalValuesSettings [MAX_PROPERTIES_ACTUATED];
int callbackDigitalValuesCount;

IOTActuator::IOTActuator(IOTActuatorSettings &actuatorSettings, IOTActuatorPublishInterface &actuatorInterface, UDP* udpClient): timeClient(*udpClient, "0.es.pool.ntp.org",0,60000, 1340) {
	this->actuatorInterface = &actuatorInterface;
	this->actuatorInterface->setNewDigitalValueCallback(newDigitalValueCallback);
	this->actuatorInterface->setNewAnalogValueCallback(newAnalogValueCallback);
	this->actuatorSettings = &actuatorSettings;
	this->lastPublishTimeStamp = 0;
	callbackAnalogValuesCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_ACTUATED; i++) {
		lastAnalogPublishedValues[i] = "";
	}
	callbackDigitalValuesCount = 0;
	for (int i = 0; i < MAX_PROPERTIES_ACTUATED; i++) {
		lastDigitalPublishedValues[i] = false;
	}
}

IOTActuator::~IOTActuator() {
}


DateTime IOTActuator::getCurrentDate() {
	timeClient.update();
	unsigned long utcEpochTime = timeClient.getEpochTime();
	unsigned long currentEpochTime = actuatorSettings->toLocalEpochTime(utcEpochTime);
	DateTime currentDate;
	currentDate.setEpochTime(currentEpochTime);
	return currentDate;
}

bool IOTActuator::loop(void) {
	this->actuatorInterface->loop();
	bool publishedValue = false;
	Value value = getValue();
	if(value.getNumberOfActuatorValues() > 0) {
		if (this->reachedPublishInterval() || this->valueChanged(value)) {
			publishedValue = this->actuatorInterface->publishValue(value);
			this->updateLastPublishedValues(value);
		}
		if (this->reachedPublishInterval() && publishedValue) {
			lastPublishTimeStamp = millis();
		}
	}
	postLoop(publishedValue, value);
	return publishedValue;
}

Value IOTActuator::getValue() {
	DateTime currentDate = getCurrentDate() ;
	Value value(currentDate);
	this->addAnalogCallbackValues(&value);
	this->addDigitalCallbackValues(&value);
	return value;
}

void IOTActuator::addAnalogCallbackValues(Value* value) {
	for (int i = 0; i < callbackAnalogValuesCount; i++) {
		AnalogPropertyActuated propertyActuated = callbackAnalogValuesSettings[i].propertyActuated;
		String newValue = callbackAnalogValuesSettings[i].getValueCallback(propertyActuated);
		value->addValue(propertyActuated, newValue);
	}
}

void IOTActuator::addDigitalCallbackValues(Value* value) {
	for (int i = 0; i < callbackDigitalValuesCount; i++) {
		DigitalPropertyActuated propertyActuated = callbackDigitalValuesSettings[i].propertyActuated;
		bool newValue = callbackDigitalValuesSettings[i].getValueCallback(propertyActuated);
		value->addValue(propertyActuated, newValue);
	}
}

bool IOTActuator::reachedPublishInterval() {
	return this->actuatorSettings->isReachedPublishInterval(millis() - lastPublishTimeStamp);
}

bool IOTActuator::valueChanged(Value value) {
	if (this->analogValuesChanged(value) || this->digitalValuesChanged(value)) {
		return true;
	}
	return false;
}

bool IOTActuator::analogValuesChanged(Value value) {
	bool analogValuesChanged = false;
	for(int i = 0; i < callbackAnalogValuesCount; i++) {
		AnalogPropertyActuated propertyActuated = callbackAnalogValuesSettings[i].propertyActuated;
		String lastValue = lastAnalogPublishedValues[i];
		if(!lastValue.equals(value.getValue(propertyActuated))) {
			analogValuesChanged = true;
		}
	}
	return analogValuesChanged;
}

bool IOTActuator::digitalValuesChanged(Value value) {
	bool digitalValuesChanged = false;
	for(int i = 0; i < callbackDigitalValuesCount; i++) {
		DigitalPropertyActuated propertyActuated = callbackDigitalValuesSettings[i].propertyActuated;
		bool lastValue = lastDigitalPublishedValues[i];
		if (lastValue != value.getValue(propertyActuated)) {
			digitalValuesChanged = true;
		}
	}
	return digitalValuesChanged;
}

void IOTActuator::updateLastPublishedValues(Value value) {
	this->updateLastAnalogPublishedValues(value);
	this->updateLastDigitalPublishedValues(value);
}

void IOTActuator::updateLastAnalogPublishedValues(Value value) {
	for (int i = 0; i < callbackAnalogValuesCount; i++) {
		AnalogPropertyActuated propertyActuated = callbackAnalogValuesSettings[i].propertyActuated;
		lastAnalogPublishedValues[i] = value.getValue(propertyActuated);
	}
}

void IOTActuator::updateLastDigitalPublishedValues(Value value) {
	for (int i = 0; i < callbackDigitalValuesCount; i++) {
		DigitalPropertyActuated propertyActuated = callbackDigitalValuesSettings[i].propertyActuated;
		lastDigitalPublishedValues[i] = value.getValue(propertyActuated);
	}
}

void IOTActuator::addCallbackValueSettings(CallbackAnalogValueSetting callbackValueSettings) {
	callbackAnalogValuesSettings[callbackAnalogValuesCount] = callbackValueSettings;
	callbackAnalogValuesCount++;
}

void IOTActuator::addCallbackValueSettings(CallbackDigitalValueSetting callbackValueSettings) {
	callbackDigitalValuesSettings[callbackDigitalValuesCount] = callbackValueSettings;
	callbackDigitalValuesCount++;
}

void IOTActuator::newAnalogValueCallback(AnalogPropertyActuated propertyActuated, String value) {
	for (int i = 0; i < callbackAnalogValuesCount; i++) {
		if (propertyActuated == callbackAnalogValuesSettings[i].propertyActuated) {
			callbackAnalogValuesSettings[i].setValueCallback(propertyActuated, value);
		}
	}
}

void IOTActuator::newDigitalValueCallback(DigitalPropertyActuated propertyActuated, bool value) {
	for (int i = 0; i < callbackDigitalValuesCount; i++) {
		if (propertyActuated == callbackDigitalValuesSettings[i].propertyActuated) {
			callbackDigitalValuesSettings[i].setValueCallback(propertyActuated, value);
		}
	}
}

