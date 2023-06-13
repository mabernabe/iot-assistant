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
const String sensor_name = "Sensor de luz ambiente";
const uint32_t max_time_msec_between_publishing = 60000;
const float diff_lux_publishing_threshold = 300;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,60000);

unsigned long lastPublishingTimeStamp = 0;
float lastLuxValue = 0;

bool publishLX1972SensorMeasure(float lux) {
  customSerial.println("Publish LX1972 lux measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addValue(ANALOG_AMBIENT_LIGHT_LUX, String(lux));
  return mqttSensorInterface.publishMeasure(measure);
}


bool diffLuxPublisthresholdReached(float currentLuxValue) {
  float difference = currentLuxValue - lastLuxValue;
  return (abs(difference) > diff_lux_publishing_threshold);
}

/* Custom handmade algorithm, no very accurate at all */
float getLuxFromAnalogValue(float value) {
  float lux;
  if (0 <= value && value < 1200) {
    lux = value/20;
  }
  else if (1200 <= value && value < 1800) {
    lux = (value/20)*4;
  }
  else if (1800 <= value && value < 2400) {
    lux = (value/20)*9;
  }
  else {
    lux = value;
  }
  return lux;
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
  float currentLuxValue = getLuxFromAnalogValue(analogValue);
  
  /* We publish when max_time between publications was reached or if there is a significative change in ambient light measure to not miss it*/ 
  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || diffLuxPublisthresholdReached(currentLuxValue)) { 
    float difference = currentLuxValue - lastLuxValue;
    if (publishLX1972SensorMeasure(currentLuxValue)) {
      lastPublishingTimeStamp = millis();
      lastLuxValue = currentLuxValue;
    }
  }
  delay(1000); // Give time to sensor value to stabilize between loops
  mqttSensorInterface.loop(); 
}
