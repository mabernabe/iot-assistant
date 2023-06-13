#include <M5AtomActuator.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttActuatorPublishInterface.h>

// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Flash";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttActuatorPublishInterface mqttActuatorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomActuatorSettings m5AtomActuatorSettings(PUBLISH_VALUE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5AtomActuator m5AtomActuator(m5AtomActuatorSettings, mqttActuatorPublishInterface, wiFiSTA.getNewUDPClient());


void setup(){
 wiFiSTA.connectLoop();
 mqttActuatorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomActuator.addGPIOValueSettings(getGPIOValueSettings());
}

DigitalGPIOValueSettings getGPIOValueSettings() {
 DigitalGPIOValueSettings gpioValueSettings;
 gpioValueSettings.propertyActuated = DIGITAL_LED;
 gpioValueSettings.gpio = ESP_GPIO_NUM_26;
 return gpioValueSettings;
}

void loop() {
  m5AtomActuator.loop();
}
