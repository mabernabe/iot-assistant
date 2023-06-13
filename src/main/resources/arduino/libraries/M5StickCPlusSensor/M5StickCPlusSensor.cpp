/*
 * M5AtomSensor.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5StickCPlusSensor.h"
#include "Datetime.h"


M5StickCPlusSensor::M5StickCPlusSensor(M5StickCPlusSensorSettings& m5StickCPlusSensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient) : ESP32Sensor(m5StickCPlusSensorSettings,sensorInterface, udpClient) {
     this->m5StickCPlusSensorSettings = &m5StickCPlusSensorSettings;
	 this->ledOnTimeStamp = 0;
}

void M5StickCPlusSensor::begin() {
	M5.begin(m5StickCPlusSensorSettings->isLCDActive(), true, true);
	if (!m5StickCPlusSensorSettings->isLCDActive()) {
		M5.Axp.SetLDO2(false);
	}
}

void M5StickCPlusSensor::deepSleep(void) {
	Serial.println("Going to sleep Stick C PLus!");
	static int SEC_TO_US_FACTOR  = 1000000;
	unsigned long publishInterval = this->m5StickCPlusSensorSettings->getPublishMeasureInterval() * SEC_TO_US_FACTOR;
	M5.Axp.DeepSleep(publishInterval);
}

void M5StickCPlusSensor::postLoop(bool published, Measure measure) {
	ESP32Sensor::postLoop(published, measure);
	if (m5StickCPlusSensorSettings->isLCDActive() && !this->sensorInterfaceInfo.equals(this->getSensorInterfaceInfo()) ) {
		this->updateLCDData(measure);
	}
}


void M5StickCPlusSensor::updateLCDData(Measure measure) {
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


