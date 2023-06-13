#include <M5StickCPlusSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5_ENV.h"

// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de ambiente";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5StickCPlusSensorSettings m5StickCPlusSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5StickCPlusSensor m5StickCPlusSensor(m5StickCPlusSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

SHT3X sht30;
QMP6988 qmp6988;

void setup(){

}


CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getTemperatureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  if (sht30.get() == 0) {
    return String(sht30.cTemp); 
  }
  return "0";
}

String getHumidityMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  if (sht30.get() == 0) {
    return String(sht30.humidity); 
  }
  return "0";
}

String getPressureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(qmp6988.calcPressure());
}

bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}


void loop() {

}
