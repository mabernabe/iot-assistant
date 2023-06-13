#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "MAX30100_PulseOximeter.h"

// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor corazon";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(NO_PUBLISH_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

PulseOximeter pox;
uint8_t heartRate = 0;
uint8_t spo2 = 0;
bool publishedBeat = false;
bool newBeat = false;


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 pox.begin();
 pox.setIRLedCurrent(MAX30100_LED_CURR_7_6MA);
 pox.setOnBeatDetectedCallback(onBeatDetected);
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_HEART_RATE_PPM, getHeartRateMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_PULSE_OXYMETER_PERCENTAGE, getPulseOxymeterMeasureCallback));
}

void onBeatDetected()
{
  heartRate = (int)pox.getHeartRate();
  spo2 = (int)pox.getSpO2();
  newBeat = true;
  publishedBeat = false;
  Serial.println(heartRate);
  Serial.println(spo2);
}


CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getHeartRateMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(heartRate);
}

String getPulseOxymeterMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(spo2);
}

bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  static const int detected_beat = 40;
  if (!publishedBeat && propertyMeasured == ANALOG_HEART_RATE_PPM && newValue.toInt() > detected_beat) {
    publishedBeat = true;
    return true;
  }
  else return false;
}

void loop() {
  int i = 0;
  while (i < 100 && !newBeat) {
    pox.update();
    i++;
  }
  if (newBeat) {
    newBeat = false;
  }
  m5AtomSensor.loop(); 
}
