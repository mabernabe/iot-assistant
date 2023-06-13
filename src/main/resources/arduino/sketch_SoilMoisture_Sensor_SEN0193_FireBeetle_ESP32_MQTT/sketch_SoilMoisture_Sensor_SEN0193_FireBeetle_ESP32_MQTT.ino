/***************************************************
 Capacitive Soil Moisture Sensor. 
 https://www.dfrobot.com/wiki/index.php?title=Capacitive_Soil_Moisture_Sensor_SKU:SEN0193

 <ma.bernabecruz@gmail.com>
 ****************************************************/
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
const uint32_t max_time_msec_between_publishing = 60000; 
const String sensor_name = "Sensor de humedad del suelo";
const float analog_value_rh_change_threshold = 15; //We consider significative change sensor value is above this diff threshold
const int sensorPin = A0;

const int AirValue = 3000;   //you need to replace this value with the value of the sensor when exposed to the air
const int WaterValue = 1300;  //you need to replace this value with the value of the sensor when full inmersed (till the safe line) in glass of water
const int rangeRH = (AirValue-WaterValue)/100;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

unsigned long lastPublishingTimeStamp = 0;
float lastSensorValue = 0;

float getRHPercentageFromAnalogValue(int analogValue) {
  float analogValueInterval = analogValue - WaterValue;
  float rhValue = analogValueInterval/rangeRH;
  return 100 - rhValue;
}

bool publishSEN0193SensorMeasure(float rh) {
  customSerial.println("Publish RH sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addAnalogValue(ANALOG_SOIL_MOISTURE_RH, rh);
  return mqttSensorInterface.publishMeasure(measure);
}

void setup() {
  wiFiSTA.connectLoop();
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
}

void loop() {
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.reconnectLoop();
  }
  int analogValue = analogRead(sensorPin);
  int currentRH = getRHPercentageFromAnalogValue(analogValue);
  bool RHHasChanged = abs(lastSensorValue - currentRH) >= analog_value_rh_change_threshold;
  /* We publish when max_time between publications is reached or if significative change in uvi status is detected*/ 
  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || RHHasChanged) { 
    if (publishSEN0193SensorMeasure(currentRH)) {
      lastPublishingTimeStamp = millis();
      lastSensorValue = currentRH;
    }
  }
  mqttSensorInterface.loop(); 
  delay(500);
}
