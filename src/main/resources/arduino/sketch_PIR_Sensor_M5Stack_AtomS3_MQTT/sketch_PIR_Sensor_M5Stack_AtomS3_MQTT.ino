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
const String topic = "Sensor de movimiento";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomS3SensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE, LCD_ACTIVE);
M5AtomS3Sensor m5AtomS3Sensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomS3Sensor.addGPIOMeasureSettings(getGPIOMeasureSettings(ESP_GPIO_NUM_2, DIGITAL_MOTION));
}

DigitalGPIOMeasureSettings getGPIOMeasureSettings(ESP32_GPIO_NUM gpio, DigitalPropertyMeasured propertyMeasured) {
 DigitalGPIOMeasureSettings gpioMeasureSettings;
 gpioMeasureSettings.propertyMeasured = propertyMeasured;
 gpioMeasureSettings.gpio = gpio;
 gpioMeasureSettings.publishOnChange = true;
 gpioMeasureSettings.invert = false;
 return gpioMeasureSettings;
}

void loop() {
  m5AtomS3Sensor.loop();
}
