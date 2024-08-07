/*
 * ESP32Actuator.h
 *
 *  Created on: 10 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef ESP32ACTUATOR_H_
#define ESP32ACTUATOR_H_
#include "ESP32ActuatorSettings.h"
#include "IOTActuator.h"

using namespace value;

namespace esp32actuator {

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

struct DigitalGPIOValueSettings {
	DigitalPropertyActuated propertyActuated;
	ESP32_GPIO_NUM gpio;
} ;

struct AnalogGPIOValueSettings {
	AnalogPropertyActuated propertyActuated;
	ESP32_GPIO_NUM gpio;
} ;


class ESP32Actuator : public IOTActuator {
public:
	ESP32Actuator(ESP32ActuatorSettings& esp32ActuatorSettings, IOTActuatorPublishInterface& actuatorInterface, UDP* udpClient);
	void addGPIOValueSettings(DigitalGPIOValueSettings gpioValueSettings);
	void addGPIOValueSettings(AnalogGPIOValueSettings gpioValueSettings);

private:
	static bool getDigitalValueCallback(DigitalPropertyActuated propertyActuated);
	static String getAnalogValueCallback(AnalogPropertyActuated propertyActuated);
	static void setDigitalValueCallback(DigitalPropertyActuated propertyActuated, bool value);
	static void setAnalogValueCallback(AnalogPropertyActuated propertyActuated, String value);
	static DigitalGPIOValueSettings getGpioValueSetting(DigitalPropertyActuated propertyActuated);
	static AnalogGPIOValueSettings getGpioValueSetting(AnalogPropertyActuated propertyActuated);

};

#endif /* ESP32ACTUATOR_H_ */

}
