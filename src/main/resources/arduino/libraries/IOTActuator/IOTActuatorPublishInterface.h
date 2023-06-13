/*
 * SensorInterface.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_bernabe
 */

#ifndef IOTACTUATORPUBLISHINTERFACE_H_
#define IOTACTUATORPUBLISHINTERFACE_H_
#include "Value.h"

using namespace value;

class IOTActuatorPublishInterface {
public:
	IOTActuatorPublishInterface();
	virtual bool publishValue(Value value) = 0;
	virtual void loop(void) = 0;
	virtual ~IOTActuatorPublishInterface();
	virtual void setNewAnalogValueCallback(void (*newAnalogValueCallback)(AnalogPropertyActuated propertyActuated, String value)) = 0;
	virtual void setNewDigitalValueCallback(void (*newDigitalValueCallback)(DigitalPropertyActuated propertyActuated, bool value)) = 0;

};

#endif /* IOTACTUATORPUBLISHINTERFACE_H_ */
