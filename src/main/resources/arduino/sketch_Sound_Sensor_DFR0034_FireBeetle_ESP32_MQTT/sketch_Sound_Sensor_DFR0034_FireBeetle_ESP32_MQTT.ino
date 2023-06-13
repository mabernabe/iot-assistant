#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "192.168.1.138";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor de sonido";
const uint32_t max_time_msec_between_publishing = 60000;
const float analog_value_sound_threshold = 200; //We consider sound is detected when analog sensor value is above this threshold

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

unsigned long lastPublishingTimeStamp = 0;
bool lastSensorValue = false;

bool publishDFR0034SensorMeasure(bool digitalValue) {
  customSerial.println("Publish DFR0032 sound sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addDigitalValue(DIGITAL_SOUND, digitalValue);
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
  float analogValue = analogRead(A0);
  bool isSound = analogValue > analog_value_sound_threshold;
  /* We publish when max_time between publications is reached or if change in sound status (sound -> not sound and viceversa) is detected*/ 
  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || isSound != lastSensorValue) { 
    if (publishDFR0034SensorMeasure(isSound)) {
      lastPublishingTimeStamp = millis();
      lastSensorValue = isSound;
    }
  }
  mqttSensorInterface.loop(); 
}
