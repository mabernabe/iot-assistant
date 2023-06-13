#include <M5AtomActuator.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttActuatorPublishInterface.h>
#include "AtomSPK.h"

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Speaker";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);

const String STOP_MP3_ID = "0";
const String ATLETI_MP3_ID = "1";
const String ATLETI_MP3_FILENAME = "/atleti.mp3";
const String SIREN_MP3_ID = "2";
const String SIREN_MP3_FILENAME = "/sirena.mp3";

MqttActuatorPublishInterface mqttActuatorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomActuatorSettings m5AtomActuatorSettings(PUBLISH_VALUE_1_MINUTE_INTERVAL, m5atomactuatorsettings::LED_ACTIVE);
M5AtomActuator m5AtomActuator(m5AtomActuatorSettings, mqttActuatorPublishInterface, wiFiSTA.getNewUDPClient());

AtomSPK speaker;
String speakerId;

void setup(){
 wiFiSTA.connectLoop();
 mqttActuatorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 m5AtomActuator.addCallbackValueSettings(getCallbackValueSettings());
 speaker.addFileIdSetting(getFileIdSetting(ATLETI_MP3_ID, ATLETI_MP3_FILENAME));
 speaker.addFileIdSetting(getFileIdSetting(SIREN_MP3_ID, SIREN_MP3_FILENAME));
}

CallbackAnalogValueSetting getCallbackValueSettings() {
 CallbackAnalogValueSetting callbackValueSettings;
 callbackValueSettings.propertyActuated = ANALOG_SPEAKER_ID;
 callbackValueSettings.getValueCallback = getSpeakedId;
 callbackValueSettings.setValueCallback = setSpeakerId;
 return callbackValueSettings;
}

String getSpeakedId(AnalogPropertyActuated propertyActuated) {
  return speakerId;
}

void setSpeakerId(AnalogPropertyActuated propertyActuated, String id) {
  if (id.equals(STOP_MP3_ID)) {
    speaker.stopMp3();
  }
  else {
    speaker.playMp3FromId(id);
    speakerId = id;
  }
}

FileIdSetting getFileIdSetting(String id, String filename) {
  FileIdSetting fileIdSetting;
  fileIdSetting.id = id;
  fileIdSetting.filename = filename;
  return fileIdSetting;
}



void loop() {
  m5AtomActuator.loop();
  speaker.loop();
  if (!speaker.isPlaying()){
    speakerId = STOP_MP3_ID;
  }
}
