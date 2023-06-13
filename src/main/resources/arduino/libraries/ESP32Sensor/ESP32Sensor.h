/*
 * ESP32Sensor.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef ESP32SENSOR_H_
#define ESP32SENSOR_H_
#include "ESP32SensorSettings.h"
#include "IOTSensor.h"

using namespace measure;

namespace esp32sensor {


enum ESP32_GPIO_NUM {
	ESP_GPIO_NUM_0 = 0,     /*!< GPIO0, input and output */
	ESP_GPIO_NUM_1 = 1,     /*!< GPIO1, input and output */
	ESP_GPIO_NUM_2 = 2,     /*!< GPIO2, input and output */
	ESP_GPIO_NUM_3 = 3,     /*!< GPIO3, input and output */
	ESP_GPIO_NUM_4 = 4,     /*!< GPIO4, input and output */
	ESP_GPIO_NUM_5 = 5,     /*!< GPIO5, input and output */
	ESP_GPIO_NUM_6 = 6,     /*!< GPIO6, input and output */
	ESP_GPIO_NUM_7 = 7,     /*!< GPIO7, input and output */
	ESP_GPIO_NUM_8 = 8,     /*!< GPIO8, input and output */
	ESP_GPIO_NUM_9 = 9,     /*!< GPIO9, input and output */
	ESP_GPIO_NUM_10 = 10,   /*!< GPIO10, input and output */
	ESP_GPIO_NUM_11 = 11,   /*!< GPIO11, input and output */
	ESP_GPIO_NUM_12 = 12,   /*!< GPIO12, input and output */
	ESP_GPIO_NUM_13 = 13,   /*!< GPIO13, input and output */
	ESP_GPIO_NUM_14 = 14,   /*!< GPIO14, input and output */
	ESP_GPIO_NUM_15 = 15,   /*!< GPIO15, input and output */
	ESP_GPIO_NUM_16 = 16,   /*!< GPIO16, input and output */
	ESP_GPIO_NUM_17 = 17,   /*!< GPIO17, input and output */
	ESP_GPIO_NUM_18 = 18,   /*!< GPIO18, input and output */
	ESP_GPIO_NUM_19 = 19,   /*!< GPIO19, input and output */
	ESP_GPIO_NUM_20 = 20,   /*!< GPIO20, input and output */
	ESP_GPIO_NUM_21 = 21,   /*!< GPIO21, input and output */
	ESP_GPIO_NUM_22 = 22,   /*!< GPIO22, input and output */
	ESP_GPIO_NUM_23 = 23,   /*!< GPIO23, input and output */
	ESP_GPIO_NUM_25 = 25,   /*!< GPIO25, input and output */
	ESP_GPIO_NUM_26 = 26,   /*!< GPIO26, input and output */
	ESP_GPIO_NUM_27 = 27,   /*!< GPIO27, input and output */
	ESP_GPIO_NUM_28 = 28,   /*!< GPIO28, input and output */
	ESP_GPIO_NUM_29 = 29,   /*!< GPIO29, input and output */
	ESP_GPIO_NUM_30 = 30,   /*!< GPIO30, input and output */
	ESP_GPIO_NUM_31 = 31,   /*!< GPIO31, input and output */
	ESP_GPIO_NUM_32 = 32,   /*!< GPIO32, input and output */
	ESP_GPIO_NUM_33 = 33,   /*!< GPIO33, input and output */
	NUMBER_OF_GPIOS = 34
};

struct DigitalGPIOMeasureSettings {
	DigitalPropertyMeasured propertyMeasured;
	ESP32_GPIO_NUM gpio;
	bool publishOnChange;
	bool invert = false;
} ;

struct AnalogGPIOMeasureSettings {
	AnalogPropertyMeasured propertyMeasured;
	ESP32_GPIO_NUM gpio;
	bool (*shouldPublishChangeCallback)(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue);
} ;

struct DeepSleepGPIOWakeSetting {
	DigitalPropertyMeasured propertyMeasured;
	ESP32_GPIO_NUM gpio;
	bool wakeUpValue;
} ;


class ESP32Sensor : public IOTSensor {
public:
	ESP32Sensor(ESP32SensorSettings& esp32SensorSettings, IOTSensorPublishInterface& sensorInterface, UDP* udpClient);
	void addGPIOMeasureSettings(DigitalGPIOMeasureSettings gpioMeasureSettings);
	void addGPIOMeasureSettings(AnalogGPIOMeasureSettings gpioMeasureSettings);

protected:
	void postLoop(bool published, Measure measure);
	void deepSleep(Measure measure);
	virtual void deepSleep();

private:
	static bool shouldPublishChangeCallback(DigitalPropertyMeasured propertyMeasured, bool lastValue, bool newValue);
	static bool getDigitalMeasureCallback(DigitalPropertyMeasured propertyMeasured);
	static String getAnalogMeasureCallback(AnalogPropertyMeasured propertyMeasured);
	static DigitalGPIOMeasureSettings getGpioMeasureSetting(DigitalPropertyMeasured propertyMeasured);
	static AnalogGPIOMeasureSettings getGpioMeasureSetting(AnalogPropertyMeasured propertyMeasured);
	void configureDeepSleepExtPins(Measure measure);
	static DeepSleepGPIOWakeSetting getDeepSleepGPIOWakeSetting(DigitalPropertyMeasured propertyMeasured);
	ESP32SensorSettings* esp32SensorSettings;

};

}

#endif /* ESP32SENSOR_H_ */
