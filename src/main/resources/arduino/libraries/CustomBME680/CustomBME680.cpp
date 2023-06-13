/*
  CustomBME680.h - CustomBME680 library - implementation
*/

#include "CustomBME680.h"


#ifdef ARDUINO_FireBeetle-ESP8266
CustomBME680::CustomBME680(int i2cAddress): bme(i2cAddress)
{
	this->bme.begin();
	this->bme.supportIAQ();
	this->bme.iaqUpdate();
}

#else
CustomBME680::CustomBME680(int i2cAddress)
{
	Wire.begin();
	this->iaqSensor.begin(i2cAddress, Wire);
	bsec_virtual_sensor_t sensorList[10] = {
		BSEC_OUTPUT_RAW_TEMPERATURE,
		BSEC_OUTPUT_RAW_PRESSURE,
		BSEC_OUTPUT_RAW_HUMIDITY,
		BSEC_OUTPUT_RAW_GAS,
		BSEC_OUTPUT_IAQ,
		BSEC_OUTPUT_STATIC_IAQ,
		BSEC_OUTPUT_CO2_EQUIVALENT,
		BSEC_OUTPUT_BREATH_VOC_EQUIVALENT,
		BSEC_OUTPUT_SENSOR_HEAT_COMPENSATED_TEMPERATURE,
		BSEC_OUTPUT_SENSOR_HEAT_COMPENSATED_HUMIDITY,
	};
	iaqSensor.updateSubscription(sensorList, 10, BSEC_SAMPLE_RATE_LP);
  
}
#endif

bool CustomBME680::acquireData(void)
{
	#ifdef ARDUINO_FireBeetle-ESP8266
	    return (this->bme.iaqUpdate()  == 0 && this->bme.isIAQReady());
	#else
		return this->iaqSensor.run();
	#endif
}


float CustomBME680::getTemperature(void)
{
	#ifdef ARDUINO_FireBeetle-ESP8266
		return this-> bme.readTemperature();
	#else
		return this->iaqSensor.temperature;
	#endif
}

float CustomBME680::getHumidity(void)
{
	#ifdef ARDUINO_FireBeetle-ESP8266
		return this->bme.readHumidity();
	#else
		return this->iaqSensor.humidity;
	#endif
}

float CustomBME680::getIAQ(void)
{
	#ifdef ARDUINO_FireBeetle-ESP8266
		return this->bme.readIAQ();
	#else
		return this->iaqSensor.staticIaq;
	#endif
}

float CustomBME680::getPressure(void)
{
	#ifdef ARDUINO_FireBeetle-ESP8266
		return this->bme.readPressure();
	#else	
		return this->iaqSensor.pressure;
	#endif
}



