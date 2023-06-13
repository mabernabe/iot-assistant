/*
  Measure.cpp
*/

#include "Measure.h"

using namespace measure;

Measure::Measure(DateTime date)
{
  this->date = date;
  digitalMeasureValuesCount = 0;
  analogMeasureValuesCount = 0;
}

int Measure::getNumberOfMeasureValues()
{
  return digitalMeasureValuesCount + analogMeasureValuesCount;
}

void Measure::addMeasureValue(DigitalPropertyMeasured propertyMeasured, bool value)
{
  DigitalMeasureValue digitalMeasureValue;
  digitalMeasureValue.propertyMeasured = propertyMeasured;
  digitalMeasureValue.value = value;
  digitalMeasureValues[digitalMeasureValuesCount] = digitalMeasureValue;
  digitalMeasureValuesCount++;
}

void Measure::addMeasureValue(AnalogPropertyMeasured propertyMeasured, String value)
{
  AnalogMeasureValue analogMeasureValue;
  analogMeasureValue.propertyMeasured = propertyMeasured;
  analogMeasureValue.value = value;
  analogMeasureValues[analogMeasureValuesCount] = analogMeasureValue;
  analogMeasureValuesCount++;
}


bool Measure::getMeasureValue(DigitalPropertyMeasured propertyMeasured)
{
  bool value;
  for (int i = 0; i < digitalMeasureValuesCount; i++) {
	  if (digitalMeasureValues[i].propertyMeasured == propertyMeasured) {
		  value = digitalMeasureValues[i].value;
	  }
  }
  return value;
}

String Measure::getMeasureValue(AnalogPropertyMeasured propertyMeasured)
{
  String value;
  for (int i = 0; i < analogMeasureValuesCount; i++) {
	  if (analogMeasureValues[i].propertyMeasured == propertyMeasured) {
		  value = analogMeasureValues[i].value;
	  }
  }
  return value;
}


String Measure::getJSONString(void)
{
  String json = String ("{ ");
  json += String("\"measures\" : {");
  json += this->getAnalogMeasuresJSON();
  if (analogMeasureValuesCount > 0 && digitalMeasureValuesCount > 0) {
	  json += ", ";
  }
  json += this->getDigitalMeasuresJSON();
  json += String("}, ");
  json += String(" \"date\" : ") + String("\"") + this->date.getFullFormattedDateTime(YYYY_MM_DD_HH_MM_SS) + String("\"");
  json+= String(" }");
  return json;
}

String Measure::getAnalogMeasuresJSON() {
	String json = String ("");
	for (int i = 0; i < analogMeasureValuesCount; i++) {
		String propertyMeasured = getStringFromEnum(analogMeasureValues[i].propertyMeasured);
		String value = analogMeasureValues[i].value;
		if (i != 0) {
			json += ", ";
		}
		json += String("\"") + propertyMeasured + String("\"") + String(" : ") + String("\"") + value + String("\"");
	}
	return json;
}

String Measure::getDigitalMeasuresJSON() {
	String json = String ("");
	for (int i = 0; i < digitalMeasureValuesCount; i++) {
		String propertyMeasured = getStringFromEnum(digitalMeasureValues[i].propertyMeasured);
		String value = this->digitalToStringValue(digitalMeasureValues[i].value);
		if (i != 0 ) {
			json += ", ";
		}
		json += String("\"") + propertyMeasured + String("\"") + String(" : ") + String("\"") + value + String("\"");
	}
	return json;
}

String Measure::digitalToStringValue(bool value)
{
  String digitalValueString = "Low";
  if (value) {
	  digitalValueString = "High";
  }
  return digitalValueString;
}

DigitalMeasureValue* Measure::getDigitalMeasureValues()
{
  return this->digitalMeasureValues;
}

AnalogMeasureValue* Measure::getAnalogMeasureValues()
{
  return this->analogMeasureValues;
}

int Measure::getDigitalMeasureCount()
{
  return this->digitalMeasureValuesCount;
}

int Measure::getAnalogMeasureCount()
{
  return this->analogMeasureValuesCount;
}


String Measure::getStringFromEnum(DigitalPropertyMeasured propertyMeasured)
{
  switch (propertyMeasured)
  {
    case DIGITAL_MOTION: return "Motion";
	case DIGITAL_FLAME: return "Flame";
    case DIGITAL_TILT: return "Tilt";
	case DIGITAL_SOUND: return "Sound";
	case DIGITAL_STEAM: return "Steam";
	case DIGITAL_GENERIC: return "Generic Digital";
	case DIGITAL_DUAL_BUTTON_A: return "Dual Button A";
	case DIGITAL_DUAL_BUTTON_B: return "Dual Button B";
	case DIGITAL_BUTTON: return "Button";
	case DIGITAL_LIMIT_SWITCH: return "Limit Switch";
    default: return("Invalid propertyMeasured");
  }
}

String Measure::getStringFromEnum(AnalogPropertyMeasured propertyMeasured)
{
  switch (propertyMeasured)
  {
    case ANALOG_TEMPERATURE_CENTIGRADES: return "Temperature C";
    case ANALOG_TEMPERATURE_FARENHEIT: return "Temperature F";
	case ANALOG_AMBIENT_LIGHT_LUX: return "Ambient Light Lux";
    case ANALOG_HUMIDITY_PERCENTAGE: return "Humidity %";
    case ANALOG_AIR_PRESSURE_PA: return "Air Pressure Pa";
    case ANALOG_AIR_QUALITY_IAQ: return "Air Quality IAQ";
    case ANALOG_UV_UVI: return "UV UVI";
	case ANALOG_UV_UVA: return "UVA nm";
	case ANALOG_UV_UVB: return "UVB nm";
	case ANALOG_AIR_CO_PPM: return "Air CO PPM";	
	case ANALOG_AIR_CO2_PPM: return "Air CO2 PPM";
	case ANALOG_AIR_TVOC_PPB: return "Air TVOC PPB";
	case ANALOG_SOIL_MOISTURE_RH: return "Soil Moisture %RH";
	case ANALOG_ENCODER_60U: return "Encoder 60U";
	case ANALOG_HEART_RATE_PPM: return "Heart Rate PPM";
	case ANALOG_PULSE_OXYMETER_PERCENTAGE: return "Pulse Oxymeter %";
	case ANALOG_GESTURE_ID: return "Gesture Id";
	case ANALOG_CURRENT_A: return "Current A";
	case ANALOG_VOLTAGE_V: return "Voltage V";
	case ANALOG_POWER_W: return "Power W";
	case ANALOG_GENERIC_NA: return "Generic Analog NA";
	default: return("Invalid propertyMeasured");
  }
}
