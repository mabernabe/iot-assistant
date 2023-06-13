#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include "MQ7.h"

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "192.168.1.138";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor de CO";
const uint32_t max_time_msec_between_publishing = 60000;
const float analog_value_ppm_change_threshold = 7; //We consider significative change sensor value is above this threshold

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);
MQ7 mq7(A0, 5.0);

unsigned long lastPublishingTimeStamp = 0;
float lastSensorValue = 0;

bool publishMQ7SensorMeasure(float ppm) {
  customSerial.println("Publish MQ7 ppm sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addAnalogValue(ANALOG_GAS_PPM, ppm);
  return mqttSensorInterface.publishMeasure(measure);
}




void setup(){
 wiFiSTA.connectLoop();
 mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
}

    
void loop() {
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.reconnectLoop();
  }
  float currentPPM = mq7.getPPM();
  if (!isnan(currentPPM)) {
    bool ppmHasChanged = abs(lastSensorValue - currentPPM) >= analog_value_ppm_change_threshold;
    /* We publish when max_time between publications is reached or if significative change in ppp status is detected*/ 
    if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || ppmHasChanged) { 
      if (publishMQ7SensorMeasure(currentPPM)) {
        lastPublishingTimeStamp = millis();
        lastSensorValue = currentPPM;
      }
    }
  }
  mqttSensorInterface.loop(); 
  delay(500);
}
