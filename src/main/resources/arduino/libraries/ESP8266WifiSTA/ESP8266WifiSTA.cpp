/*
  ESP8266WifiSTA.h - ESP8266WiFiSTA library - implementation
*/

#include "ESP8266WifiSTA.h"
#include <ESP8266WiFi.h>

ESP8266WifiSTA::ESP8266WifiSTA(String ssid, String password, CustomSerial& customSerial)
{
  this->ssid = ssid;
  this->password = password;
  this->customSerial = &customSerial;
}

void ESP8266WifiSTA::connectLoop(void)
{
  delay(10);
  this->customSerial->println();
  this->customSerial->print("Connecting to ");
  this->customSerial->println(this->ssid);

  WiFi.mode(WIFI_STA);
  WiFi.setAutoReconnect(true);
  
  #ifdef ARDUINO_FireBeetle-ESP8266
	WiFi.begin(this->ssid.c_str(), this->password.c_str(), 0 , NULL, true);
  #else
	WiFi.begin(this->ssid, this->password);
  #endif

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    this->customSerial->print(".");
  }

  randomSeed(micros());

  this->customSerial->println("");
  this->customSerial->println("WiFi connected");
  this->customSerial->println("IP address: ");
  this->customSerial->println(WiFi.localIP());
}

Client* ESP8266WifiSTA::getNewClient(void)
{
  return new WiFiClient();
}

UDP* ESP8266WifiSTA::getNewUDPClient(void)
{
  return new WiFiUDP();
}


