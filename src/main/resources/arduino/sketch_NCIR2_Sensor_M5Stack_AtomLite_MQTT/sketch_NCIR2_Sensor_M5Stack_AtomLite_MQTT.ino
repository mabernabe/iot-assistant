#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5UNIT_NCIR2.h"


// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor ncir";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(NO_PUBLISH_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

M5UNIT_NCIR2 ncir2;


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 ncir2.begin(&Wire, 26, 32, M5UNIT_NCIR2_DEFAULT_ADDR);
 ncir2.disable_buzz();
 ncir2.setConfig();
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_TEMPERATURE_CENTIGRADES, getTemperatureMeasureCallback));
}


CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getTemperatureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return (String)((float)ncir2.getTempValue() / 100);
}


bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return ncir2.getButtonStatus() == 0;
}


void loop() {
  m5AtomSensor.loop();
}
