/*
 * SensorInterface.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef IOTSENSORPUBLISHINTERFACE_H_
#define IOTSENSORPUBLISHINTERFACE_H_
#include "Measure.h"

using namespace measure;

class IOTSensorPublishInterface {
public:
	IOTSensorPublishInterface();
	virtual bool publishMeasure(Measure measure) = 0;
	virtual void loop(void) = 0;
	virtual void disconnect(void) = 0;
	virtual bool connected(void) = 0;
	virtual ~IOTSensorPublishInterface();
	virtual String getInfo(void) = 0;
};

#endif /* IOTSENSORPUBLISHINTERFACE_H_ */
