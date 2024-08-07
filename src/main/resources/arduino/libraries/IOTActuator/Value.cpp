/*
	Value.cpp - 
 */

#include "Value.h"

using namespace value;


Value::Value(DateTime date)
{
	this->date = date;
	digitalActuatorValuesCount = 0;
	analogActuatorValuesCount = 0;
}

int Value::getNumberOfActuatorValues()
{
	return digitalActuatorValuesCount + analogActuatorValuesCount;
}


void Value::addValue(DigitalPropertyActuated propertyActuated, bool value)
{
	DigitalActuatorValue digitalActuatorValue;
	digitalActuatorValue.propertyActuated = propertyActuated;
	digitalActuatorValue.value = value;
	digitalActuatorValues[digitalActuatorValuesCount] = digitalActuatorValue;
	digitalActuatorValuesCount++;
}

void Value::addValue(AnalogPropertyActuated propertyActuated, String value)
{
	AnalogActuatorValue analogActuatorValue;
	analogActuatorValue.propertyActuated = propertyActuated;
	analogActuatorValue.value = value;
	analogActuatorValues[analogActuatorValuesCount] = analogActuatorValue;
	analogActuatorValuesCount++;
}

bool Value::getValue(DigitalPropertyActuated propertyActuated)
{
	bool value;
	for (int i = 0; i < digitalActuatorValuesCount; i++) {
		if (digitalActuatorValues[i].propertyActuated == propertyActuated) {
			value = digitalActuatorValues[i].value;
		}
	}
	return value;
}

String Value::getValue(AnalogPropertyActuated propertyActuated)
{
	String value;
	for (int i = 0; i < analogActuatorValuesCount; i++) {
		if (analogActuatorValues[i].propertyActuated == propertyActuated) {
			value = analogActuatorValues[i].value;
		}
	}
	return value;
}



String Value::getJSONString(void)
{
	String json = String ("{ ");
	json += String("\"values\" : {");
	json += this->getAnalogValuesJSON();
	if (analogActuatorValuesCount > 0 && digitalActuatorValuesCount > 0) {
		json += ", ";
	}
	json += this->getDigitalValuesJSON();
	json += String("}, ");
	json += String(" \"date\" : ") + String("\"") + this->date.getFullFormattedDateTime(YYYY_MM_DD_HH_MM_SS) + String("\"");
	json+= String(" }");
	return json;
}

String Value::getAnalogValuesJSON() {
	String json = String ("");
	for (int i = 0; i < analogActuatorValuesCount; i++) {
		String propertyActuated = Value::getStringFromEnum(analogActuatorValues[i].propertyActuated);
		String value = analogActuatorValues[i].value;
		if (i != 0) {
			json += ", ";
		}
		json += String("\"") + propertyActuated + String("\"") + String(" : ") + String("\"") + value + String("\"");
	}
	return json;
}

String Value::getDigitalValuesJSON() {
	String json = String ("");
	for (int i = 0; i < digitalActuatorValuesCount; i++) {
		String propertyActuated = Value::getStringFromEnum(digitalActuatorValues[i].propertyActuated);
		String value = this->digitalToStringValue(digitalActuatorValues[i].value);
		if (i != 0 ) {
			json += ", ";
		}
		json += String("\"") + propertyActuated + String("\"") + String(" : ") + String("\"") + value + String("\"");
	}
	return json;
}

String Value::digitalToStringValue(bool value)
{
	String digitalValueString = "Low";
	if (value) {
		digitalValueString = "High";
	}
	return digitalValueString;
}

bool Value::stringToDigitalValue(String value)
{
	bool digitalValue = false;
	if (value.equals("High")) {
		digitalValue = true;
	}
	return digitalValue;
}


String Value::getStringFromEnum(DigitalPropertyActuated propertyActuated)
{
	switch (propertyActuated)
	{
	case DIGITAL_GENERIC: return "Generic Digital";
	case DIGITAL_LED: return "Led";
	case DIGITAL_RELAY: return "Relay";
	case DIGITAL_SOCKET: return "Socket";
	default: return("Invalid propertyActuated");
	}
}

String Value::getStringFromEnum(AnalogPropertyActuated propertyActuated)
{
	switch (propertyActuated)
	{
	case ANALOG_SPEAKER_ID: return "Speaker Id";
	case ANALOG_GENERIC_NA: return "Generic Analog NA";
	default: return("Invalid propertyMeasured");
	}
}

bool Value::getAnalogPropertyActuatedFromCharArray(const char* enumCharArray, AnalogPropertyActuated& propertyActuated)
{
	bool error = true;
	for (int i = 0; i < NUMBER_OF_ANALOG_PROPERTIES_ACTUATED; i++) {
		AnalogPropertyActuated analogPropertyInstance = static_cast<AnalogPropertyActuated>(i);
		if (strcmp(enumCharArray, getStringFromEnum(analogPropertyInstance).c_str()) == 0) {
			propertyActuated = analogPropertyInstance;
			error = false;
		}
	}
	return error;
}

bool Value::getDigitalPropertyActuatedFromCharArray(const char* enumCharArray, DigitalPropertyActuated& propertyActuated)
{
	bool error = true;
	for (int i = 0; i < NUMBER_OF_DIGITAL_PROPERTIES_ACTUATED; i++) {
		DigitalPropertyActuated digitalPropertyInstance = static_cast<DigitalPropertyActuated>(i);
		if (strcmp(enumCharArray, getStringFromEnum(digitalPropertyInstance).c_str()) == 0) {
			propertyActuated = digitalPropertyInstance;
			error = false;
		}
	}
	return error;
}
