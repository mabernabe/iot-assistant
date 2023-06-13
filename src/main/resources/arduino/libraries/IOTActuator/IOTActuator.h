/*
 * IOTActuator.h
 *
 *  Created on: 11 mar 2022
 *      Author: miguel_bernabe
 */

#ifndef IOTACTUATOR_H_
#define IOTACTUATOR_H_
#include <NTPClient.h>
#include "IOTActuatorPublishInterface.h"
#include "IOTActuatorSettings.h"

using namespace value;

struct CallbackAnalogValueSetting {
	AnalogPropertyActuated propertyActuated;
	String (*getValueCallback)(AnalogPropertyActuated propertyActuated);
	void (*setValueCallback)(AnalogPropertyActuated propertyActuated, String value);
} ;

struct CallbackDigitalValueSetting {
	DigitalPropertyActuated propertyActuated;
	bool (*getValueCallback)(DigitalPropertyActuated propertyActuated);
	void (*setValueCallback)(DigitalPropertyActuated propertyActuated, bool value);
} ;

class IOTActuator {
public:
	IOTActuator(IOTActuatorSettings& actuatorSettings, IOTActuatorPublishInterface& actuatorInterface, UDP* udpClient);
	virtual ~IOTActuator();
	bool loop(void);
	void addCallbackValueSettings(CallbackAnalogValueSetting callbackValueSetting);
	void addCallbackValueSettings(CallbackDigitalValueSetting callbackValueSetting);

protected:
	DateTime getCurrentDate();

private:
	IOTActuatorPublishInterface* actuatorInterface;
	IOTActuatorSettings* actuatorSettings;
	unsigned long lastPublishTimeStamp;
	NTPClient timeClient;
	String lastAnalogPublishedValues [MAX_PROPERTIES_ACTUATED];
	bool lastDigitalPublishedValues [MAX_PROPERTIES_ACTUATED];

	Value getValue();
	static void newAnalogValueCallback(AnalogPropertyActuated propertyActuated, String value);
	static void newDigitalValueCallback(DigitalPropertyActuated propertyActuated, bool value);
	void addAnalogCallbackValues(Value* value);
	void addDigitalCallbackValues(Value* value);
	bool publishValue(Value value);
	bool reachedPublishInterval();
	bool valueChanged(Value value);
	bool analogValuesChanged(Value value);
	bool digitalValuesChanged(Value value);
	void updateLastPublishedValues(Value value);
	void updateLastAnalogPublishedValues(Value value);
	void updateLastDigitalPublishedValues(Value value);
	virtual void postLoop(bool published, Value value) = 0;
};

#endif /* IOTActuator_H_ */
