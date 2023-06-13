/*
  ESP8266WiFiSTA.h 
*/


#ifndef ESP8266WifiSTA_h
#define ESP8266WifiSTA_h

#include <Ethernet.h>
#include "CustomSerial.h"
#include <WiFiUdp.h>


class ESP8266WifiSTA
{
	
  private:
  String ssid;
  String password;
  CustomSerial* customSerial;

  public:
    ESP8266WifiSTA(String ssid, String password, CustomSerial& customSerial);
	void connectLoop(void);
	Client* getNewClient(void);
	UDP* getNewUDPClient(void);
};

#endif