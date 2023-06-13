#include <M5AtomActuator.h>
#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include <MqttActuatorPublishInterface.h>
#include "AtomSocket.h"

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensorTopic = "Socket sensor";
const String actuatorTopic = "Socket actuator";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);

MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), sensorTopic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_20_SEC_INTERVAL, m5atomsensorsettings::LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

MqttActuatorPublishInterface mqttActuatorPublishInterface(*wiFiSTA.getNewClient(), actuatorTopic, customSerial);
M5AtomActuatorSettings m5AtomActuatorSettings(PUBLISH_VALUE_1_MINUTE_INTERVAL, m5atomactuatorsettings::LED_ACTIVE);
M5AtomActuator m5AtomActuator(m5AtomActuatorSettings, mqttActuatorPublishInterface, wiFiSTA.getNewUDPClient());

ATOMSOCKET atom;
HardwareSerial atomSerial(2);

struct SocketProperties {
  int voltage = 0;
  int activePower = 0;
  float current = 0;
  bool relayState = false;
} ;

SocketProperties socketProperties;

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 mqttActuatorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 atom.Init(atomSerial, 23, 22);
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_CURRENT_A, getCurrentMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_VOLTAGE_V, getVoltageMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_POWER_W, getPowerMeasureCallback));
 m5AtomActuator.addCallbackValueSettings(getCallbackValueSettings());
}

CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}


String getCurrentMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketProperties.current);
}

String getVoltageMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketProperties.voltage);
}

String getPowerMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(socketProperties.activePower);
}

bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}

CallbackDigitalValueSetting getCallbackValueSettings() {
 CallbackDigitalValueSetting callbackValueSettings;
 callbackValueSettings.propertyActuated = DIGITAL_SOCKET;
 callbackValueSettings.getValueCallback = getRelayStateCallback;
 callbackValueSettings.setValueCallback = setRelayStateCallback;
 return callbackValueSettings;
}


bool getRelayStateCallback(DigitalPropertyActuated propertyActuated) {
  return socketProperties.relayState;
}

void setRelayStateCallback(DigitalPropertyActuated propertyActuated, bool value) {
  socketProperties.relayState = value;
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
  if (socketProperties.relayState) {
    atom.SetPowerOn();
  } else {
    atom.SetPowerOff();
  }
  m5AtomActuator.loop();
  m5AtomSensor.loop();
}

void updateSocketMeasureProperties() {
  socketProperties.voltage = atom.GetVol();
  socketProperties.current = atom.GetCurrent();
  socketProperties.activePower = atom.GetActivePower();
}
