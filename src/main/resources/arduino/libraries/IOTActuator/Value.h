/*
	Value.h 
 */


#ifndef Value_h
#define Value_h

#include <Arduino.h>
#include <DateTime.h>

namespace value {

enum AnalogPropertyActuated {
	ANALOG_SPEAKER_ID = 0,
	ANALOG_GENERIC_NA,
	NUMBER_OF_ANALOG_PROPERTIES_ACTUATED
};

enum DigitalPropertyActuated {
	DIGITAL_GENERIC = 0,
	DIGITAL_LED,
	DIGITAL_RELAY,
	DIGITAL_SOCKET,
	NUMBER_OF_DIGITAL_PROPERTIES_ACTUATED
};

struct DigitalActuatorValue
{
	DigitalPropertyActuated propertyActuated;
	bool value;
};

struct AnalogActuatorValue
{
	AnalogPropertyActuated propertyActuated;
	String value;
};

const byte MAX_PROPERTIES_ACTUATED = 5;

class Value
{

public:
	Value(DateTime date);
	int getNumberOfActuatorValues();
	void addValue(AnalogPropertyActuated propertyActuated, String value);
	void addValue(DigitalPropertyActuated propertyActuated, bool value);
	String getValue(AnalogPropertyActuated propertyActuated);
	bool getValue(DigitalPropertyActuated propertyActuated);
	String getJSONString(void);
	static bool getAnalogPropertyActuatedFromCharArray(const char* enumCharArray, AnalogPropertyActuated& propertyActuated);
	static bool getDigitalPropertyActuatedFromCharArray(const char* enumCharArray, DigitalPropertyActuated& propertyActuated);
	static bool stringToDigitalValue(String value);

private:
	DigitalActuatorValue digitalActuatorValues[MAX_PROPERTIES_ACTUATED];
	int digitalActuatorValuesCount;
	AnalogActuatorValue analogActuatorValues[MAX_PROPERTIES_ACTUATED];
	int analogActuatorValuesCount;
	static String getStringFromEnum(AnalogPropertyActuated propertyActuated);
	static String getStringFromEnum(DigitalPropertyActuated propertyActuated);
	String getAnalogValuesJSON();
	String getDigitalValuesJSON();
	static String digitalToStringValue(bool value);
	DateTime date;
};

}

#endif
