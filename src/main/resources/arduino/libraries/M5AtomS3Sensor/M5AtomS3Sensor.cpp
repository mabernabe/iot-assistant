/*
 * M5AtomSensor.cpp
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#include "M5AtomS3Sensor.h"
#include "Datetime.h"


M5AtomS3Sensor::M5AtomS3Sensor(M5AtomS3SensorSettings& m5AtomS3SensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient) : ESP32Sensor(m5AtomS3SensorSettings,sensorInterface, udpClient) {
	 M5.begin(m5AtomS3SensorSettings.isLCDActive() ,  true, true, m5AtomS3SensorSettings.isLedActive());
     this->m5AtomS3SensorSettings = &m5AtomS3SensorSettings;
	 this->ledOnTimeStamp = 0;
	 this->sensorInterfaceInfo = String("");
	 this->ledOn = false;

}

void M5AtomS3Sensor::postLoop(bool published, Measure measure) {
	ESP32Sensor::postLoop(published, measure);
	if (m5AtomS3SensorSettings->isLCDActive() && !this->sensorInterfaceInfo.equals(this->getSensorInterfaceInfo()) ) {
		this->updateLCDData(measure);
	}
	if (published && m5AtomS3SensorSettings->isLedActive()) {
		turnLedOn();
		ledOnTimeStamp = millis();
	}
	if (this->ledOn) {
		unsigned long LedOnElapsedTime = millis() - ledOnTimeStamp ;
		if (m5AtomS3SensorSettings->isReachedLedOnInterval(LedOnElapsedTime)) {
			turnLedOff();
		}
	}
}

void M5AtomS3Sensor::updateLCDData(Measure measure) {
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

void M5AtomS3Sensor::turnLedOn() {
	M5.dis.drawpix(0x00ff00);
	this->ledOn = true;
	M5.dis.show();

}

void M5AtomS3Sensor::turnLedOff() {
	M5.dis.clear();
	this->ledOn = false;
}

