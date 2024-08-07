/*
 * IOTGpsPublishInterface.h
 *
 *  Created on: 11 mar 2022
 *      Author: migue_t0ro5dr
 */

#ifndef IOTGPSPUBLISHINTERFACE_H_
#define IOTGPSPUBLISHINTERFACE_H_
#include "Position.h"

using namespace position;

class IOTGpsPublishInterface {
public:
	IOTGpsPublishInterface();
	virtual bool publishPosition(Position position) = 0;
	virtual void loop(void) = 0;
	virtual void disconnect(void) = 0;
	virtual bool connected(void) = 0;
	virtual String getInfo(void) = 0;
	virtual ~IOTGpsPublishInterface();
};

#endif /* IOTGPSPUBLISHINTERFACE_H_ */
