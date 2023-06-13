/*
  ESP32WifiSTA.h - ESP32WifiSTA library - implementation
*/

#include "ESP32WifiSTA.h"
#include <WiFi.h>

ESP32WifiSTA::ESP32WifiSTA(String ssid, String password, CustomSerial& customSerial)
{
  this->ssid = ssid;
  this->password = password;
  this->customSerial = &customSerial;
}

void ESP32WifiSTA::connectLoop(void)
{
  delay(10);
  this->customSerial->println();
  this->customSerial->print("Connecting to ");
  this->customSerial->println(this->ssid);
  WiFi.mode(WIFI_STA);
  WiFi.setAutoReconnect(true); 
  WiFi.begin(this->ssid.c_str(), this->password.c_str());
  restartIfCantConnect();
  randomSeed(micros());
  this->customSerial->println("");
  this->customSerial->println("WiFi connected");
  this->customSerial->println("IP address: ");
  this->customSerial->println(WiFi.localIP());
}

void ESP32WifiSTA::restartIfCantConnect(void) {
  int secPassed = 0;
  while (!isConnected() && secPassed <  ESP32WifiSTA::CONNECT_WAIT_SECONDS_TO_RESTART) {
	int secToMsecFactor = 1000;
	delay(1 * secToMsecFactor); 
	secPassed++;
	this->customSerial->print(".");		
  }
  if (!isConnected()) {
	ESP.restart();
  }
}

bool ESP32WifiSTA::isConnected(void) {
	return WiFi.status() == WL_CONNECTED && WiFi.localIP() != IPAddress(0,0,0,0);
}


void ESP32WifiSTA::watchdogRestart(void)
{
  if (!isConnected()) {
	this->customSerial->println("Got disconnected");
	restartIfCantConnect();
  }
  
}

Client* ESP32WifiSTA::getNewClient(void)
{
  return new WiFiClient();
}

UDP* ESP32WifiSTA::getNewUDPClient(void)
{
  return new WiFiUDP();
}


