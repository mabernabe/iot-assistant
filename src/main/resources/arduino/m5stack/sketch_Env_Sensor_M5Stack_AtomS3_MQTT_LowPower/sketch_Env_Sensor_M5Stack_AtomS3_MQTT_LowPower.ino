#include <M5AtomS3Sensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5UnitENV.h"

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "7f6a0b40dedf4d84b0c1f6250c512408.s1.eu.hivemq.cloud";
const uint16_t mqtt_server_port = 8883;
const String topic = "Ambiente salon";


CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomS3SensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE, LCD_ACTIVE);
M5AtomS3Sensor m5AtomS3Sensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 m5AtomS3Sensor.begin();
 wiFiSTA.connectLoop();
 AtomS3.dis.setBrightness(100);
 AtomS3.Lcd.println("Before wifi loop");
 AtomS3.Lcd.println("After wifi loop");
 AtomS3.Lcd.println("after wire init");
 AtomS3.Lcd.println("after set callbacks");
}



void loop() {
  delay(1000);
  AtomS3.Lcd.println("loop");
}
