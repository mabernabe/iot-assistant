/*
  Measure.h 
*/


#ifndef Measure_h
#define Measure_h

#include <Arduino.h>
#include <DateTime.h>

namespace measure {

enum DigitalPropertyMeasured {
	DIGITAL_MOTION = 0,
	DIGITAL_FLAME,
	DIGITAL_TILT,
	DIGITAL_SOUND,
	DIGITAL_STEAM,
	DIGITAL_BUTTON,
	DIGITAL_DUAL_BUTTON_A,
	DIGITAL_DUAL_BUTTON_B,
	DIGITAL_LIMIT_SWITCH,
	DIGITAL_GENERIC
};

enum AnalogPropertyMeasured {
	ANALOG_TEMPERATURE_CENTIGRADES = 0,
	ANALOG_TEMPERATURE_FARENHEIT,
	ANALOG_AMBIENT_LIGHT_LUX,
	ANALOG_HUMIDITY_PERCENTAGE,
	ANALOG_AIR_PRESSURE_PA,
	ANALOG_AIR_QUALITY_IAQ,
	ANALOG_UV_UVI,
	ANALOG_UV_UVA,
	ANALOG_UV_UVB,
	ANALOG_AIR_CO_PPM,
	ANALOG_AIR_CO2_PPM,
	ANALOG_AIR_TVOC_PPB,
	ANALOG_SOIL_MOISTURE_RH,
	ANALOG_ENCODER_60U,
	ANALOG_HEART_RATE_PPM,
	ANALOG_PULSE_OXYMETER_PERCENTAGE,
	ANALOG_GESTURE_ID,
	ANALOG_CURRENT_A,
	ANALOG_VOLTAGE_V,
	ANALOG_POWER_W,
	ANALOG_GENERIC_NA
};


struct DigitalMeasureValue
{
	DigitalPropertyMeasured propertyMeasured;
	bool value;
};

struct AnalogMeasureValue
{
	AnalogPropertyMeasured propertyMeasured;
	String value;
};

const byte MAX_PROPERTIES_MEASURED = 5;

class Measure
{
	
  public:
    Measure(DateTime date);
    int getNumberOfMeasureValues();
	void addMeasureValue(DigitalPropertyMeasured propertyMeasured, bool value);
	void addMeasureValue(AnalogPropertyMeasured propertyMeasured, String value);
	bool getMeasureValue(DigitalPropertyMeasured propertyMeasured);
	String getMeasureValue(AnalogPropertyMeasured propertyMeasured);
	String getJSONString(void);
	DigitalMeasureValue* getDigitalMeasureValues();
	AnalogMeasureValue* getAnalogMeasureValues();
	int getDigitalMeasureCount();
    int getAnalogMeasureCount();
    String getStringFromEnum(DigitalPropertyMeasured propertyMeasured);
    String getStringFromEnum(AnalogPropertyMeasured propertyMeasured);

  private:
    DigitalMeasureValue digitalMeasureValues[MAX_PROPERTIES_MEASURED];
    int digitalMeasureValuesCount;
    AnalogMeasureValue analogMeasureValues[MAX_PROPERTIES_MEASURED];
    int analogMeasureValuesCount;
    String getAnalogMeasuresJSON();
    String getDigitalMeasuresJSON();
    static String digitalToStringValue(bool value);
    DateTime date;
};

}

#endif
