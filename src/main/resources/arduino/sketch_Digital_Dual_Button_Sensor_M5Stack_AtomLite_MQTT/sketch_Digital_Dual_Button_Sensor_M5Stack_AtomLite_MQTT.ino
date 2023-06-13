#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>

// WiFi Settings.
const char* wifi_ssid = "NuCom_7077F0";
const char* wifi_password = "eBbLySD1TabK";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Boton dual";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomSensor.addGPIOMeasureSettings(getGPIOMeasureSettings(ESP_GPIO_NUM_26, DIGITAL_DUAL_BUTTON_A));
 m5AtomSensor.addGPIOMeasureSettings(getGPIOMeasureSettings(ESP_GPIO_NUM_32, DIGITAL_DUAL_BUTTON_B));
}

DigitalGPIOMeasureSettings getGPIOMeasureSettings(ESP32_GPIO_NUM gpio, DigitalPropertyMeasured propertyMeasured) {
 DigitalGPIOMeasureSettings gpioMeasureSettings;
 gpioMeasureSettings.propertyMeasured = propertyMeasured;
 gpioMeasureSettings.gpio = gpio;
 gpioMeasureSettings.publishOnChange = true;
 gpioMeasureSettings.invert = true;
 return gpioMeasureSettings;
}

void loop() {
  m5AtomSensor.loop();
}
