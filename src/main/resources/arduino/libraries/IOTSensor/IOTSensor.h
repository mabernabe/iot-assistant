/*
 * IOTSensor.h
 *
 *  Created on: 11 mar 2022
 *      Author: miguel_bernabe
 */

#ifndef IOTSENSOR_H_
#define IOTSENSOR_H_
#include <NTPClient.h>
#include "IOTSensorPublishInterface.h"
#include "IOTSensorSettings.h"

using namespace measure;

struct AnalogMeasureSetting {
	AnalogPropertyMeasured propertyMeasured;
	bool (*shouldPublishChange)(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue);
	String (*getMeasure)(AnalogPropertyMeasured propertyMeasured);
} ;

struct DigitalMeasureSetting{
	DigitalPropertyMeasured propertyMeasured;
	bool (*shouldPublishChange)(DigitalPropertyMeasured propertyMeasured, bool lastValue, bool newValue);
	bool (*getMeasure)(DigitalPropertyMeasured propertyMeasured);
} ;

class IOTSensor {
public:
	IOTSensor(IOTSensorSettings& sensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);
	virtual ~IOTSensor();
	bool loop(void);
	void addMeasureSetting(AnalogMeasureSetting callbackMeasureSetting);
	void addMeasureSetting(DigitalMeasureSetting callbackMeasureSetting);
	void setIsMeasureReadyCallback(bool (*isMeasureReadyCallback)());


protected:
	DateTime getCurrentDate();
	String getSensorInterfaceInfo();
	void disconnect();
	bool connected();

private:
	IOTSensorPublishInterface* sensorInterface;
	IOTSensorSettings* sensorSettings;
	unsigned long lastPublishTimeStamp;
	NTPClient timeClient;
	AnalogMeasureSetting analogMeasureSettings [MAX_PROPERTIES_MEASURED];
	int analogMeasureSettingsCount;
	DigitalMeasureSetting digitalMeasureSettings [MAX_PROPERTIES_MEASURED];
	int digitalMeasureSettingsCount;
	String analogPublishedValues [MAX_PROPERTIES_MEASURED];
	bool digitalPublishedValues [MAX_PROPERTIES_MEASURED];
	bool isFirstPublish;
	bool (*isMeasureReadyCallback)();

	Measure getMeasure();
	void addAnalogValue(Measure* measure);
	void addDigitalValue(Measure* measure);
	bool publishMeasure(Measure measure);
	bool reachedMeasureInterval();
	bool shouldPublishMeasureChange(Measure measure);
	bool shouldPublishAnalogMeasureChange(Measure measure);
	bool shouldPublishDigitalMeasureChange(Measure measure);
	void updatePublishedValues(Measure measure);
	void updateAnalogPublishedValues(Measure measure);
	void updateDigitalPublishedValues(Measure measure);
	virtual void postLoop(bool published, Measure measure) = 0;
};

#endif /* IOTSENSOR_H_ */
