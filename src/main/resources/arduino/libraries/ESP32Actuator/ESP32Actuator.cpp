/*
 * ESP32Actuator.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32Actuator.h"

using namespace esp32actuator;

static DigitalGPIOValueSettings digitalGPIOValuesSettings [MAX_PROPERTIES_ACTUATED];
static int digitalGPIOValuesSettingsCount;

static AnalogGPIOValueSettings analogGPIOValuesSettings [MAX_PROPERTIES_ACTUATED];
static int analogGPIOValuesSettingsCount;

ESP32Actuator::ESP32Actuator(ESP32ActuatorSettings& esp32ActuatorSettings, IOTActuatorPublishInterface& actuatorInterface, UDP* udpClient):  IOTActuator(esp32ActuatorSettings, actuatorInterface, udpClient){
	digitalGPIOValuesSettingsCount = 0;
	analogGPIOValuesSettingsCount = 0;
}


void ESP32Actuator::addGPIOValueSettings(DigitalGPIOValueSettings gpioValueSettings) {
	CallbackDigitalValueSetting callbackValuesSettings;
	callbackValuesSettings.propertyActuated = gpioValueSettings.propertyActuated;
	callbackValuesSettings.getValueCallback = this->getDigitalValueCallback;
	callbackValuesSettings.setValueCallback = this->setDigitalValueCallback;
	pinMode(gpioValueSettings.gpio, OUTPUT);
	digitalGPIOValuesSettings[digitalGPIOValuesSettingsCount] = gpioValueSettings;
	digitalGPIOValuesSettingsCount++;
	this->addCallbackValueSettings(callbackValuesSettings);
}

void ESP32Actuator::addGPIOValueSettings(AnalogGPIOValueSettings gpioValueSettings) {
	CallbackAnalogValueSetting callbackValuesSettings;
	callbackValuesSettings.propertyActuated = gpioValueSettings.propertyActuated;
	callbackValuesSettings.getValueCallback = this->getAnalogValueCallback;
	callbackValuesSettings.setValueCallback = this->setAnalogValueCallback;
	pinMode(gpioValueSettings.gpio, OUTPUT);
	analogGPIOValuesSettings[analogGPIOValuesSettingsCount] = gpioValueSettings;
	analogGPIOValuesSettingsCount++;
	this->addCallbackValueSettings(callbackValuesSettings);
}



DigitalGPIOValueSettings ESP32Actuator::getGpioValueSetting(DigitalPropertyActuated propertyActuated) {
	DigitalGPIOValueSettings gpioValueSettings;
	for (int i = 0; i < digitalGPIOValuesSettingsCount; i++) {
		if(digitalGPIOValuesSettings[i].propertyActuated == propertyActuated ) {
			gpioValueSettings =  digitalGPIOValuesSettings[i];
		}
	}
	return gpioValueSettings;
}

AnalogGPIOValueSettings ESP32Actuator::getGpioValueSetting(AnalogPropertyActuated propertyActuated) {
	AnalogGPIOValueSettings gpioValueSettings;
	for (int i = 0; i < analogGPIOValuesSettingsCount; i++) {
		if(analogGPIOValuesSettings[i].propertyActuated == propertyActuated ) {
			gpioValueSettings =  analogGPIOValuesSettings[i];
		}
	}
	return gpioValueSettings;
}

bool ESP32Actuator::getDigitalValueCallback(DigitalPropertyActuated propertyActuated) {
	DigitalGPIOValueSettings gpioValueSetting = ESP32Actuator::getGpioValueSetting(propertyActuated);
	return digitalRead(gpioValueSetting.gpio);
}

String ESP32Actuator::getAnalogValueCallback(AnalogPropertyActuated propertyActuated) {
	AnalogGPIOValueSettings gpioValueSetting = ESP32Actuator::getGpioValueSetting(propertyActuated);
	return String(analogRead(gpioValueSetting.gpio));
}

void ESP32Actuator::setDigitalValueCallback(DigitalPropertyActuated propertyActuated, bool value) {
	DigitalGPIOValueSettings gpioValueSetting = ESP32Actuator::getGpioValueSetting(propertyActuated);
	digitalWrite(gpioValueSetting.gpio, value);
}

void ESP32Actuator::setAnalogValueCallback(AnalogPropertyActuated propertyActuated, String value) {
	AnalogGPIOValueSettings gpioValueSetting = ESP32Actuator::getGpioValueSetting(propertyActuated);
	analogWrite(gpioValueSetting.gpio, value.toInt());
}

