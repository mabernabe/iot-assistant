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
const String sensor_name = "Sensor de lluvia";
const uint32_t max_time_msec_between_publishing = 60000;
const float analog_value_steam_detected_threshold = 2000; //We consider steam is detected when analog sensor value is above this threshold

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

unsigned long lastPublishingTimeStamp = 0;
bool lastSensorValue = false;

bool publishSEN0121SensorMeasure(bool digitalValue) {
  customSerial.println("Publish SEN0121 steam sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addDigitalValue(DIGITAL_STEAM, digitalValue);
  return mqttSensorInterface.publishMeasure(measure);
}


bool isMotionDetected(){
  return digitalRead(32);
}

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
 pinMode(32, INPUT); 
}

    
void loop() {
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.connectLoop();
  }
  bool isMotionDetected = isMotionDetected();
  /* We publish when max_time between publications is reached or if change motion status (motion detected -> not motion detected and viceversa) is detected*/ 
  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || isMotionDetected != lastSensorValue) { 
    if (publishSEN0121SensorMeasure(isSteamDetected)) {
      lastPublishingTimeStamp = millis();
      lastSensorValue = isMotionDetected;
    }
  }
  mqttSensorInterface.loop(); 
  delay(500);
}
