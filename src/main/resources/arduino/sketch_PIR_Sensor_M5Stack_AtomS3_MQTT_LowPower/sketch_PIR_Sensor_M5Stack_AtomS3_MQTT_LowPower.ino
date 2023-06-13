#include <M5AtomS3Sensor.h>
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
M5AtomS3SensorSettings m5AtomS3SensorSettings(DEEP_SLEEP_ACTIVE, PUBLISH_MEASURE_1_MINUTE_INTERVAL);
M5AtomSensor m5AtomS3Sensor(m5AtomS3SensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomS3Sensor.addGPIOMeasureSettings(getGPIOMeasureSettings());
}

DigitalGPIOMeasureSettings getGPIOMeasureSettings() {
 DigitalGPIOMeasureSettings gpioMeasureSettings;
 gpioMeasureSettings.propertyMeasured = DIGITAL_MOTION;
 gpioMeasureSettings.gpio = ESP_GPIO_NUM_1;
 gpioMeasureSettings.publishOnChange = true;
 return gpioMeasureSettings;
}

void loop() {
  m5AtomS3Sensor.loop();
}
