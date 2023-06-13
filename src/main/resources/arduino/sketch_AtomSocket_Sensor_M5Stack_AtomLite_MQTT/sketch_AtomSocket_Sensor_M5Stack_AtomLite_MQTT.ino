#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "AtomSocket.h"

// WiFi Settings.
const char* wifi_ssid = "NuCom_7077F0";
const char* wifi_password = "eBbLySD1TabK";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Socket";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_20_SEC_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

ATOMSOCKET atom;
HardwareSerial atomSerial(2);

struct SocketMeasureProperties {
  int voltage = 0;
  int activePower = 0;
  float current = 0;
} ;

SocketMeasureProperties socketMeasureProperties;

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 atom.Init(atomSerial, 23, 22);
 atom.SetPowerOn();
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_CURRENT_A, getCurrentMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_VOLTAGE_V, getVoltageMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_POWER_W, getPowerMeasureCallback));
}



CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getCurrentMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketMeasureProperties.current);
}

String getVoltageMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketMeasureProperties.voltage);
}

String getPowerMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketMeasureProperties.activePower);
}


bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}

void loop() {
  int i = 0;
  bool updated = false;
  while (i < 100 && !updated) {
    atom.SerialReadLoop();
    updated = atom.SerialRead == 1;
    i++;
  }
  if (updated) {
    updateSocketMeasureProperties();
  }
  m5AtomSensor.loop();
}

void updateSocketMeasureProperties() {
  socketMeasureProperties.voltage = atom.GetVol();
  socketMeasureProperties.current = atom.GetCurrent();
  socketMeasureProperties.activePower = atom.GetActivePower();
}
