/*
	MqttSensorInterface.h - MqttSensorInterface library for Sensors - implementation
 */

#include <PubSubClient.h>
#include "MqttSensorPublishInterface.h"


MqttSensorPublishInterface::MqttSensorPublishInterface( Client& client, String sensorTopic, CustomSerial& customSerial): mqttClient(client)
{
	this->sensorTopic = sensorTopic;
	this->customSerial = &customSerial;
	this->setAuth("", "");
}

void MqttSensorPublishInterface::setBroker(const char* brokerURL, uint16_t brokerPort)
{
	this->mqttClient.setServer(brokerURL, brokerPort);
}

void MqttSensorPublishInterface::setAuth(String username, String password)
{
	this->username = username;
	this->password = password;
}

void MqttSensorPublishInterface::setCustomSerial(CustomSerial& customSerial)
{
	this->customSerial = &customSerial;
}

void MqttSensorPublishInterface::connectLoop(void)
{
	while (!tryConnect()) {
		println(" try again in 5 seconds");
		delay(5000);   
	}
}

bool MqttSensorPublishInterface::tryConnect(void)
{
	if (!this->mqttClient.connected()) {
		print("Attempting MQTT connection...");
		String clientId = "IotAssistantClient-";
		clientId += this->sensorTopic;
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

void MqttSensorPublishInterface::print(String string)
{
	if (this->customSerial != NULL) {
		this->customSerial->print(string);
	}
}

void MqttSensorPublishInterface::print(int integer)
{
	if (this->customSerial != NULL) {
		this->customSerial->print(integer);
	}
}

void MqttSensorPublishInterface::println(String string)
{
	if (this->customSerial != NULL) {
		this->customSerial->println(string);
	}
}

bool MqttSensorPublishInterface::connect(void)
{
	int i = 0;
	while (!tryConnect() && i < 2) {
		println(" try again in 5 seconds");
		delay(5000);
		i++;
	}
	return this->mqttClient.connected();
}

bool MqttSensorPublishInterface::connected(void)
{
	return this->mqttClient.connected() && this->mqttClient.state() != MQTT_DISCONNECTED;
}

void MqttSensorPublishInterface:: loop(void)
{
	if (!connected()) {
		connectLoop();
	}
	this->mqttClient.loop();
}

String MqttSensorPublishInterface:: getInfo(void)
{
	String info = String ("MQTT: \n");
	String connected = this->connected() ? String ("Connected") : String ("Not Connected");
	info += String("	State: ") + connected + String("\n");
	info += String("	Topic: ") + this->sensorTopic + String("\n");
	return info;
}

bool MqttSensorPublishInterface:: publishMeasure(Measure measure)
{
	bool published = this->mqttClient.publish(this->sensorTopic.c_str(), measure.getJSONString().c_str(), true);
	this->mqttClient.flush();
	return published;
}

void MqttSensorPublishInterface::disconnect()
{
	this->mqttClient.disconnect();
	this->mqttClient.flush();
}

