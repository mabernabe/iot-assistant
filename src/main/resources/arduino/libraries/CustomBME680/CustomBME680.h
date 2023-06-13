/*
  CustomBME680.h 
*/


#ifndef CustomBME680_h
#define CustomBME680_h


#ifdef ARDUINO_FireBeetle-ESP8266
	#include "DFRobot_BME680_I2C.h"
#else
	#include "bsec.h"
#endif

/** BME680 I2C addresses */
#define BME680_I2C_ADDR_PRIMARY		UINT8_C(0x76)
#define BME680_I2C_ADDR_SECONDARY	UINT8_C(0x77)

class CustomBME680
{
	
  private:
  #ifdef ARDUINO_FireBeetle-ESP8266
	DFRobot_BME680_I2C bme;
  #else
	Bsec iaqSensor;
  #endif

  public:
    CustomBME680(int i2cAddress);
	bool acquireData(void);
	float getTemperature(void);
	float getHumidity(void);
	float getIAQ(void);
	float getPressure(void);
};

#endif