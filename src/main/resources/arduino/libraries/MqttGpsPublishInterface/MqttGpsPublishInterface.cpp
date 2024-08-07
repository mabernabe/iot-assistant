/*
	MqttGpsInterface.h - MqttGpsInterface library for Gpses - implementation
 */

#include <PubSubClient.h>
#include "MqttGpsPublishInterface.h"


MqttGpsPublishInterface::MqttGpsPublishInterface( Client& client, String gpsTopic, CustomSerial& customSerial): mqttClient(client)
{
	this->gpsTopic = gpsTopic;
	this->customSerial = &customSerial;
	this->setAuth("", "");
}

void MqttGpsPublishInterface::setBroker(const char* brokerURL, uint16_t brokerPort)
{
	this->mqttClient.setServer(brokerURL, brokerPort);
}

void MqttGpsPublishInterface::setAuth(String username, String password)
{
	this->username = username;
	this->password = password;
}

void MqttGpsPublishInterface::setCustomSerial(CustomSerial& customSerial)
{
	this->customSerial = &customSerial;
}

void MqttGpsPublishInterface::connectLoop(void)
{
	while (!tryConnect()) {
		println(" try again in 5 seconds");
		delay(5000);   
	}
}

bool MqttGpsPublishInterface::tryConnect(void)
{
	if (!this->mqttClient.connected()) {
		print("Attempting MQTT connection...");
		String clientId = "IotAssistantClient-";
		clientId += this->gpsTopic;
		if (this->mqttClient.connect(clientId.c_str(), this->username.c_str(), this->password.c_str())) {
			println("connected");
			return true;
		}
		else {
			print("failed, rc=");
			print(this->mqttClient.state());
			return false;
		}
	}
	return true;
}

void MqttGpsPublishInterface::print(String string)
{
	if (this->customSerial != NULL) {
		this->customSerial->print(string);
	}
}

void MqttGpsPublishInterface::print(int integer)
{
	if (this->customSerial != NULL) {
		this->customSerial->print(integer);
	}
}

void MqttGpsPublishInterface::println(String string)
{
	if (this->customSerial != NULL) {
		this->customSerial->println(string);
	}
}

bool MqttGpsPublishInterface::connect(void)
{
	int i = 0;
	while (!tryConnect() && i < 2) {
		println(" try again in 5 seconds");
		delay(5000);
		i++;
	}
	return this->mqttClient.connected();
}

bool MqttGpsPublishInterface::connected(void)
{
	return this->mqttClient.connected() && this->mqttClient.state() != MQTT_DISCONNECTED;
}

void MqttGpsPublishInterface:: loop(void)
{
	if (!connected()) {
		connectLoop();
	}
	this->mqttClient.loop();
}

String MqttGpsPublishInterface:: getInfo(void)
{
	String info = String ("MQTT: \n");
	String connected = this->connected() ? String ("Connected") : String ("Not Connected");
	info += String("	State: ") + connected + String("\n");
	info += String("	Topic: ") + this->gpsTopic + String("\n");
	return info;
}

bool MqttGpsPublishInterface:: publishPosition(Position position)
{
	bool published = this->mqttClient.publish(this->gpsTopic.c_str(), position.getJSONString().c_str(), true);
	this->mqttClient.flush();
	return published;
}

void MqttGpsPublishInterface::disconnect()
{
	this->mqttClient.disconnect();
	this->mqttClient.flush();
}

