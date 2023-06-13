/*
  ESP32WifiSTA.h 
*/


#ifndef ESP32WifiSTA_h
#define ESP32WifiSTA_h

#include <Ethernet.h>
#include "CustomSerial.h"
#include <WiFiUdp.h>


class ESP32WifiSTA
{
	
  private:
  static const int CONNECT_WAIT_SECONDS_TO_RESTART = 15;
  String ssid;
  String password;
  CustomSerial* customSerial;
  void restartIfCantConnect(void);
  bool isConnected(void);

  public:
    ESP32WifiSTA(String ssid, String password, CustomSerial& customSerial);
	void connectLoop(void);
	void watchdogRestart(void);
	Client* getNewClient(void);
	UDP* getNewUDPClient(void);
};

#endif