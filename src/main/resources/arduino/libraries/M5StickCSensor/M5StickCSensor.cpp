/*
 * M5StickCSensor.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5StickCSensor.h"
#include "Datetime.h"


M5StickCSensor::M5StickCSensor(M5StickCSensorSettings& m5StickCSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient) : ESP32Sensor(m5StickCSensorSettings,sensorInterface, udpClient) {
     this->m5StickCSensorSettings = &m5StickCSensorSettings;
	 this->ledOnTimeStamp = 0;
}

void M5StickCSensor::begin() {
	M5.begin(m5StickCSensorSettings->isLCDActive(), true, true);
	if (!m5StickCSensorSettings->isLCDActive()) {
		M5.Axp.SetLDO2(false);
		M5.Axp.SetLDO2(false);
	}
}

void M5StickCSensor::deepSleep(void) {
	Serial.println("Going to sleep Stick C!");
	static int SEC_TO_US_FACTOR  = 1000000;
	unsigned long publishInterval = this->m5StickCSensorSettings->getPublishMeasureInterval() * SEC_TO_US_FACTOR;
	M5.Axp.DeepSleep(publishInterval);
}

void M5StickCSensor::postLoop(bool published, Measure measure) {
	ESP32Sensor::postLoop(published, measure);
	if (m5StickCSensorSettings->isLCDActive() && !this->sensorInterfaceInfo.equals(this->getSensorInterfaceInfo()) ) {
		this->updateLCDData(measure);
	}
}


void M5StickCSensor::updateLCDData(Measure measure) {
	this->sensorInterfaceInfo = this->getSensorInterfaceInfo();
	M5.Lcd.println("SENSOR");
	M5.Lcd.println();
	M5.Lcd.print(this->sensorInterfaceInfo);
	M5.Lcd.println();
	M5.Lcd.println("PROPERTIES:");
	if(measure.getNumberOfMeasureValues() > 0) {
		DigitalMeasureValue *digitalMeasureValues = measure.getDigitalMeasureValues();
		for (int i = 0; i < measure.getDigitalMeasureCount(); i++) {
			DigitalMeasureValue digitalMeasureValue = digitalMeasureValues[i];
			M5.Lcd.println(measure.getStringFromEnum(digitalMeasureValue.propertyMeasured));
		}
		AnalogMeasureValue *analogMeasureValues = measure.getAnalogMeasureValues();
		for (int i = 0; i < measure.getAnalogMeasureCount(); i++) {
			AnalogMeasureValue analogMeasureValue = analogMeasureValues[i];
			M5.Lcd.println(measure.getStringFromEnum(analogMeasureValue.propertyMeasured));
		}
	}
}


