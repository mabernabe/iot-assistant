#include <M5AtomS3Sensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5_ENV.h"

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

SHT3X sht30(0x44,2);
QMP6988 qmp6988;

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 Wire.begin(2, 1);  
 qmp6988.init();
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_TEMPERATURE_CENTIGRADES, getTemperature));
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_HUMIDITY_PERCENTAGE, getHumidity));
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_AIR_PRESSURE_PA, getPressure));
 m5AtomS3Sensor.setIsMeasureReadyCallback(isMeasureReadyCallback);
}


AnalogMeasureSetting buildMeasureSetting(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 AnalogMeasureSetting analogMeasureSetting;
 analogMeasureSetting.propertyMeasured = analogPropertyMeasured;
 analogMeasureSetting.getMeasure = getMeasureCallback;
 analogMeasureSetting.shouldPublishChange = shouldPublishChange;
 return analogMeasureSetting;
}

String getTemperature(AnalogPropertyMeasured propertyMeasured) {
  return String(sht30.cTemp); 
}

String getHumidity(AnalogPropertyMeasured propertyMeasured) {
  return String(sht30.humidity);
}

String getPressure(AnalogPropertyMeasured propertyMeasured) {
  return "100";
}

bool shouldPublishChange(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}

bool isMeasureReadyCallback() {
  return sht30.get() == 0;
}

void loop() {
  qmp6988.calcPressure();
  m5AtomS3Sensor.loop();
}
