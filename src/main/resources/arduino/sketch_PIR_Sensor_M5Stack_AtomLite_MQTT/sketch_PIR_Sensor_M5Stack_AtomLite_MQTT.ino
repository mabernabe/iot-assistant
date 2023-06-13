#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de movimientos";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(DEEP_SLEEP_ACTIVE
, PUBLISH_MEASURE_1_MINUTE_INTERVAL);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomSensor.addGPIOMeasureSettings(getGPIOMeasureSettings());
}

DigitalGPIOMeasureSettings getGPIOMeasureSettings() {
 DigitalGPIOMeasureSettings gpioMeasureSettings;
 gpioMeasureSettings.propertyMeasured = DIGITAL_MOTION;
 gpioMeasureSettings.gpio = ESP_GPIO_NUM_32;
 gpioMeasureSettings.publishOnChange = true;
 return gpioMeasureSettings;
}

void loop() {
  m5AtomSensor.loop();
}
