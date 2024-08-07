/*
	MqttActuatorPublishInterface.h - MqttActuatorPublishInterface library for actuators - implementation
*/

#include "MqttActuatorPublishInterface.h"

#include <PubSubClient.h>
#include <ArduinoJson.h>

void (*newAnalogValueCallback)(AnalogPropertyActuated propertyActuated, String value);
void (*newDigitalValueCallback)(DigitalPropertyActuated propertyActuated, bool value);

MqttActuatorPublishInterface::MqttActuatorPublishInterface( Client& client, String actuatorTopic, CustomSerial& customSerial): mqttClient(client)
{
	this->actuatorTopic = actuatorTopic;
	this->setValueTopic = actuatorTopic + "/setvalue";
	this->customSerial = &customSerial;
	newAnalogValueCallback = NULL;
	newDigitalValueCallback = NULL;
}

void MqttActuatorPublishInterface::setBroker(const char* brokerURL, uint16_t brokerPort)
{
	this->mqttClient.setServer(brokerURL, brokerPort);
}


void MqttActuatorPublishInterface::connectLoop(void)
{
	while (!tryConnectAndSubscribe()) {
		this->customSerial->println(" try again in 5 seconds");
		delay(5000);
	}
}

bool MqttActuatorPublishInterface::tryConnectAndSubscribe(void)
{
	if (!this->mqttClient.connected()) {
		this->customSerial->print("Attempting MQTT connection...");
		String clientId = "IotAssistantClient-";
		clientId += this->sensorTopic;
		if (this->mqttClient.connect(clientId.c_str())) {
			this->customSerial->println("connected");
			this->mqttClient.subscribe(this->setValueTopic.c_str());
			this->mqttClient.setCallback(newMsgCallback);
			return true;
			} else {
			this->customSerial->print("failed, rc=");
			this->customSerial->print(this->mqttClient.state());
			return false;
		}
	}
}

bool MqttActuatorPublishInterface::isSetStateTopic(char* topic) {
	if (strcmp(topic, this->setValueTopic.c_str()) == 0) {
		return true;
	}
	else {
		return false;
	}
}



void MqttActuatorPublishInterface::setNewAnalogValueCallback(void (*newValueCallback)(AnalogPropertyActuated propertyActuated, String value))
{
	newAnalogValueCallback = newValueCallback;
}

void MqttActuatorPublishInterface::setNewDigitalValueCallback(void (*newValueCallback)(DigitalPropertyActuated propertyActuated, bool value))
{
	newDigitalValueCallback = newValueCallback;
}

void MqttActuatorPublishInterface::newMsgCallback(char* topic, byte* payload, unsigned int length)
{
	StaticJsonDocument<128> doc;
	DeserializationError error = deserializeJson(doc, payload);
	if (error) {
		return;
	}
	const char* propertyActuatedChar = doc["propertyActuated"];
	const char* valueChar = doc["value"];
	if (!propertyActuatedChar || !valueChar) {
		return;
	}
	String value = String(valueChar);
	AnalogPropertyActuated analogPropertyActuated;
	if (newAnalogValueCallback != NULL && !Value::getAnalogPropertyActuatedFromCharArray(propertyActuatedChar, analogPropertyActuated)) {
		newAnalogValueCallback(analogPropertyActuated, value);
	}
	DigitalPropertyActuated digitalPropertyActuated;
	if (newDigitalValueCallback != NULL && !Value::getDigitalPropertyActuatedFromCharArray(propertyActuatedChar, digitalPropertyActuated)) {
		newDigitalValueCallback(digitalPropertyActuated, Value::stringToDigitalValue(value));
	}
	doc.clear();

}


bool MqttActuatorPublishInterface::connect(void)
{
	int i = 0;
	while (!tryConnectAndSubscribe() && i < 2) {
		this->customSerial->println(" try again in 5 seconds");
		delay(5000);	
		i++;
	}
	return this->mqttClient.connected();
}



bool MqttActuatorPublishInterface::connected(void)
{
	return this->mqttClient.connected();
}

void MqttActuatorPublishInterface:: loop(void)
{
	if (!connected()) {
		connectLoop();
	}
	this->mqttClient.loop();
}

bool MqttActuatorPublishInterface:: publishValue(Value value)
{
	bool published = this->mqttClient.publish(this->actuatorTopic.c_str(), value.getJSONString().c_str(), true);
	this->mqttClient.flush();
	return published;
}

