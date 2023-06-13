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

struct CallbackAnalogMeasureSetting {
	AnalogPropertyMeasured propertyMeasured;
	bool (*shouldPublishChangeCallback)(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue);
	String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured);
} ;

struct CallbackDigitalMeasureSetting {
	DigitalPropertyMeasured propertyMeasured;
	bool (*shouldPublishChangeCallback)(DigitalPropertyMeasured propertyMeasured, bool lastValue, bool newValue);
	bool (*getMeasureCallback)(DigitalPropertyMeasured propertyMeasured);
} ;

class IOTSensor {
public:
	IOTSensor(IOTSensorSettings& sensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);
	virtual ~IOTSensor();
	bool loop(void);
	void addCallbackMeasureSettings(CallbackAnalogMeasureSetting callbackMeasureSetting);
	void addCallbackMeasureSettings(CallbackDigitalMeasureSetting callbackMeasureSetting);


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
	CallbackAnalogMeasureSetting callbackAnalogMeasuresSettings [MAX_PROPERTIES_MEASURED];
	int callbackAnalogMeasuresCount;
	CallbackDigitalMeasureSetting callbackDigitalMeasuresSettings [MAX_PROPERTIES_MEASURED];
	int callbackDigitalMeasuresCount;
	String lastAnalogPublishedValues [MAX_PROPERTIES_MEASURED];
	bool lastDigitalPublishedValues [MAX_PROPERTIES_MEASURED];
	bool firstPublication;

	Measure getMeasure();
	void addAnalogCallbackMeasures(Measure* measure);
	void addDigitalCallbackMeasures(Measure* measure);
	bool publishMeasure(Measure measure);
	bool reachedMeasureInterval();
	bool shouldPublishMeasureChange(Measure measure);
	bool shouldPublishAnalogMeasureChange(Measure measure);
	bool shouldPublishDigitalMeasureChange(Measure measure);
	void updateLastPublishedValues(Measure measure);
	void updateLastAnalogPublishedValues(Measure measure);
	void updateLastDigitalPublishedValues(Measure measure);
	virtual void postLoop(bool published, Measure measure) = 0;
};

#endif /* IOTSENSOR_H_ */
