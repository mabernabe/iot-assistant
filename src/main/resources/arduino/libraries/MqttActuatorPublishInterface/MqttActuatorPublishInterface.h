/*
	MqttActuatorInterface.h 
 */


#ifndef MqttActuatorInterface_h
#define MqttActuatorInterface_h


#include "PubSubClient.h"
#include "CustomSerial.h"
#include <Ethernet.h>

#include "IOTActuatorPublishInterface.h"
#include "Value.h"


class MqttActuatorPublishInterface : public IOTActuatorPublishInterface
{

public:
	MqttActuatorPublishInterface(Client& client, String actuatorTopic, CustomSerial& customSerial);
	void setBroker(const char* brokerURL, uint16_t brokerPort);
	bool publishValue(Value value);
	bool isSetStateTopic(char* topic);
	void setNewAnalogValueCallback(void (*newAnalogValueCallback)(AnalogPropertyActuated propertyActuated, String value));
	void setNewDigitalValueCallback(void (*newDigitalValueCallback)(DigitalPropertyActuated propertyActuated, bool value));
	bool connected();
	void connectLoop();
	bool connect();
	void loop();

private:
	PubSubClient mqttClient;
	String actuatorTopic;
	String setValueTopic;
	bool tryConnectAndSubscribe(void);
	static void newMsgCallback(char* topic, byte* payload, unsigned int length);
	CustomSerial* customSerial;
	void setNewValueCallback(void (*receivedMessageCallback)(char*, byte*, unsigned int));

};

#endif
