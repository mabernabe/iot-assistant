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
const String sensor_name = "Sensor de UV";
const uint32_t max_time_msec_between_publishing = 60000; 
const float analog_value_uvi_change_threshold = 1; //We consider significative change sensor value is above this threshold
const int sensorPin = A0;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

unsigned long lastPublishingTimeStamp = 0;
float lastSensorValue = 0;

bool publishSEN0162SensorMeasure(int uvi) {
  customSerial.println("Publish UVI sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addValue(ANALOG_UV_UVI, String(uvi));
  return mqttSensorInterface.publishMeasure(measure);
}


int getUVIFromSEN0162Value(int sen0162Value) {
  int uvi;
  if (sen0162Value<20)
  {
    uvi = 0;
  }
  else
  {
    uvi = 0.05*sen0162Value-1;
  }
  return uvi;
}

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
}

    
void loop() {
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.reconnectLoop();
  }
  int analogValue = analogRead(sensorPin);
  int currentUVI = getUVIFromSEN0162Value(analogValue);
  bool UVIHasChanged = abs(lastSensorValue - currentUVI) >= analog_value_uvi_change_threshold;
  /* We publish when max_time between publications is reached or if significative change in uvi status is detected*/ 
  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || UVIHasChanged) { 
    if (publishSEN0162SensorMeasure(currentUVI)) {
      lastPublishingTimeStamp = millis();
      lastSensorValue = currentUVI;
    }
  }
  
  mqttSensorInterface.loop(); 
  delay(500);
}
