#include <M5StickCPlusSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de movimiento stick";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5StickCPlusSensorSettings m5StickCPlusSensorSettings(DEEP_SLEEP_ACTIVE, PUBLISH_MEASURE_30_MINUTE_INTERVAL);
M5StickCPlusSensor m5StickCPlusSensor(m5StickCPlusSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 m5StickCPlusSensor.begin();
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5StickCPlusSensor.addGPIOMeasureSettings(getGPIOMeasureSettings());
}

DigitalGPIOMeasureSettings getGPIOMeasureSettings() {
 DigitalGPIOMeasureSettings gpioMeasureSettings;
 gpioMeasureSettings.propertyMeasured = DIGITAL_MOTION;
 gpioMeasureSettings.gpio = ESP_GPIO_NUM_33;
 gpioMeasureSettings.publishOnChange = true;
 return gpioMeasureSettings;
}

void loop() {
  m5StickCPlusSensor.loop();
}
