/*
 * ESP32Sensor.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "ESP32Sensor.h"

using namespace esp32sensor;

static DigitalGPIOMeasureSettings digitalGPIOMeasuresSettings [MAX_PROPERTIES_MEASURED];
static int digitalGPIOMeasuresSettingsCount;

static AnalogGPIOMeasureSettings analogGPIOMeasuresSettings [MAX_PROPERTIES_MEASURED];
static int analogGPIOMeasuresSettingsCount;

RTC_DATA_ATTR DeepSleepGPIOWakeSetting deepSleepGPIOWakeSettings [MAX_PROPERTIES_MEASURED];

ESP32Sensor::ESP32Sensor(ESP32SensorSettings& esp32SensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient):  IOTSensor(esp32SensorSettings, sensorInterface, udpClient){
	digitalGPIOMeasuresSettingsCount = 0;
	analogGPIOMeasuresSettingsCount = 0;
	this->esp32SensorSettings = &esp32SensorSettings;
}


void ESP32Sensor::addGPIOMeasureSettings(DigitalGPIOMeasureSettings gpioMeasureSettings) {
	DigitalMeasureSetting measureSetting;
	measureSetting.propertyMeasured = gpioMeasureSettings.propertyMeasured;
	measureSetting.shouldPublishChange = this->shouldPublishChange;
	measureSetting.getMeasure = this->getDigitalMeasure;
	pinMode(gpioMeasureSettings.gpio, INPUT);
	digitalGPIOMeasuresSettings[digitalGPIOMeasuresSettingsCount] = gpioMeasureSettings;
	digitalGPIOMeasuresSettingsCount++;
	this->addMeasureSetting(measureSetting);
}

void ESP32Sensor::addGPIOMeasureSettings(AnalogGPIOMeasureSettings gpioMeasureSettings) {
	AnalogMeasureSetting measureSetting;
	measureSetting.propertyMeasured = gpioMeasureSettings.propertyMeasured;
	measureSetting.shouldPublishChange = gpioMeasureSettings.shouldPublishChange;
	measureSetting.getMeasure = this->getAnalogMeasure;
	analogGPIOMeasuresSettings[analogGPIOMeasuresSettingsCount] = gpioMeasureSettings;
	analogGPIOMeasuresSettingsCount++;
	this->addMeasureSetting(measureSetting);
}

bool ESP32Sensor::shouldPublishChange(DigitalPropertyMeasured propertyMeasured, bool lastValue, bool newValue) {
	DigitalGPIOMeasureSettings gpioMeasureSetting = ESP32Sensor::getGpioMeasureSetting(propertyMeasured);
	if (gpioMeasureSetting.publishOnChange && lastValue != newValue) {
		return true;
	}
	return false;
}

DigitalGPIOMeasureSettings ESP32Sensor::getGpioMeasureSetting(DigitalPropertyMeasured propertyMeasured) {
	DigitalGPIOMeasureSettings gpioMeasureSettings;
	for (int i = 0; i < digitalGPIOMeasuresSettingsCount; i++) {
		if(digitalGPIOMeasuresSettings[i].propertyMeasured == propertyMeasured ) {
			gpioMeasureSettings =  digitalGPIOMeasuresSettings[i];
		}
	}
	return gpioMeasureSettings;
}

AnalogGPIOMeasureSettings ESP32Sensor::getGpioMeasureSetting(AnalogPropertyMeasured propertyMeasured) {
	AnalogGPIOMeasureSettings gpioMeasureSettings;
	for (int i = 0; i < analogGPIOMeasuresSettingsCount; i++) {
		if(analogGPIOMeasuresSettings[i].propertyMeasured == propertyMeasured ) {
			gpioMeasureSettings =  analogGPIOMeasuresSettings[i];
		}
	}
	return gpioMeasureSettings;
}

DeepSleepGPIOWakeSetting ESP32Sensor::getDeepSleepGPIOWakeSetting(DigitalPropertyMeasured propertyMeasured) {
	DeepSleepGPIOWakeSetting deepSleepGPIOWakeSetting;
	for (int i = 0; i < digitalGPIOMeasuresSettingsCount; i++) {
		if(deepSleepGPIOWakeSettings[i].propertyMeasured == propertyMeasured ) {
			deepSleepGPIOWakeSetting =  deepSleepGPIOWakeSettings[i];
		}
	}
	return deepSleepGPIOWakeSetting;
}

bool ESP32Sensor::getDigitalMeasure(DigitalPropertyMeasured propertyMeasured) {
	DigitalGPIOMeasureSettings gpioMeasureSetting = ESP32Sensor::getGpioMeasureSetting(propertyMeasured);

	bool value = digitalRead(gpioMeasureSetting.gpio);
	if (gpioMeasureSetting.invert) {
		value = !value;
	}
	if (esp_sleep_get_wakeup_cause() ==  ESP_SLEEP_WAKEUP_EXT0) {
		Serial.print("GPIO caused wake up ");
		uint64_t gpioWaken = esp_sleep_get_ext1_wakeup_status();
		ESP32_GPIO_NUM gpio = static_cast<ESP32_GPIO_NUM>(log(gpioWaken)/log(2));
		if (gpioMeasureSetting.gpio == gpio || digitalGPIOMeasuresSettingsCount == 1) 	{	// some esp32 dont get well the gpio that woken up
			Serial.println("We woken up with ");
			value = ESP32Sensor::getDeepSleepGPIOWakeSetting(propertyMeasured).wakeUpValue;
		}
	}
	return value;
}

String ESP32Sensor::getAnalogMeasure(AnalogPropertyMeasured propertyMeasured) {
	AnalogGPIOMeasureSettings gpioMeasureSetting = ESP32Sensor::getGpioMeasureSetting(propertyMeasured);
	return String(analogRead(gpioMeasureSetting.gpio));
}

void ESP32Sensor::postLoop(bool published, Measure measure) {
	if (this->esp32SensorSettings->isDeepSleepEnabled())  {
		this->deepSleep(measure);
	}
}

void ESP32Sensor::deepSleep(Measure measure) {
	static int SEC_TO_US_FACTOR  = 1000000;
	static int WAKE_UP_DELAY_SEC  = 3; // Time esp32 takes to wake up, connect network, publish measure...
	unsigned long publishInterval = this->esp32SensorSettings->getPublishMeasureInterval() * SEC_TO_US_FACTOR;
	esp_sleep_enable_timer_wakeup(publishInterval - WAKE_UP_DELAY_SEC * SEC_TO_US_FACTOR);
	this->configureDeepSleepExtPins(measure);
	Serial.println("Going to sleep");
	this->disconnect();
	while (this->connected()) {
		delay(100);
	}
	delay(1000);  // Wait 1 second for the network buffer to get flushed
	this->deepSleep();
}

void ESP32Sensor::deepSleep(void) {
	esp_deep_sleep_start();
}

void ESP32Sensor::configureDeepSleepExtPins(Measure measure) {
	DigitalMeasureValue *digitalMeasureValues = measure.getDigitalMeasureValues();
	for (int i = 0; i < digitalGPIOMeasuresSettingsCount; i++) {
		for (int j = 0; j < measure.getDigitalMeasureCount(); j++) {
			DigitalMeasureValue digitalMeasureValue = digitalMeasureValues[j];
			if(digitalGPIOMeasuresSettings[i].propertyMeasured == digitalMeasureValue.propertyMeasured ) {
				deepSleepGPIOWakeSettings[i].gpio =  digitalGPIOMeasuresSettings[i].gpio;
				deepSleepGPIOWakeSettings[i].wakeUpValue =  digitalGPIOMeasuresSettings[i].invert? digitalMeasureValue.value : !digitalMeasureValue.value;
				Serial.println("We go to sleep with " + String(deepSleepGPIOWakeSettings[i].wakeUpValue));
				esp_sleep_enable_ext0_wakeup((gpio_num_t)deepSleepGPIOWakeSettings[i].gpio, deepSleepGPIOWakeSettings[i].wakeUpValue);
			}
		}

	}
}



