#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttActuatorInterface.h>
#include <Value.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String actuator_name = "speaker";
const uint32_t max_time_msec_between_publishing = 60000; 

const uint8_t speakerMelodyOff = 0; 
const uint8_t speakerMelodyOn = 1;

const int sensorPin = D2;
uint8_t speakerMelody = speakerMelodyOff;
unsigned long lastPublishingTimeStamp = 0;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttActuatorInterface mqttActuatorInterface(*client, actuator_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);


boolean publishSpeakerActuatorValue(uint8_t speakerMelody) {
  customSerial.println("Publish speaker actuator value");
  timeClient.update();
  DateTime currentDate;
  currentDate.setEpochTime(timeClient.getEpochTime());
  Value value(currentDate);
  value.addAnalogValue(ANALOG_SPEAKER_SOUNDID, speakerMelody);
  return mqttActuatorInterface.publishValue(value);
}

void setMelody(uint8_t speakerMelody) {
  ledcSetup(0,1E5,12);
  ledcAttachPin(sensorPin,0);
  if (speakerMelody == 1) {
    ledcWriteNote(0,NOTE_C,1); 
  }
  else {
    ledcWriteTone(0, 0);
  }
}

void processSetStateMessage(byte* payload) {
  customSerial.println("Processing set melody message");
  Value newValue;
  boolean error = mqttActuatorInterface.processSetStateMessage(payload, newValue);
  if (!error) {
    String valueString = newValue.getValue(ANALOG_SPEAKER_SOUNDID);
    speakerMelody = valueString.toInt();
    setMelody(speakerMelody);
    publishSpeakerActuatorValue(speakerMelody);  
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  customSerial.println("Arrived new message");
  if (mqttActuatorInterface.isSetStateTopic(topic)) {
    processSetStateMessage(payload);
  }
}


void setup(){
 wiFiSTA.connectLoop();
 mqttActuatorInterface.setBroker(mqtt_server, mqtt_server_port);
 mqttActuatorInterface.setCallback(callback);
 setMelody(speakerMelody);
}

    
void loop() {
  if (!mqttActuatorInterface.connected()) {
    mqttActuatorInterface.connectLoop();
  }

  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || lastPublishingTimeStamp == 0) { 
    if (publishSpeakerActuatorValue(speakerMelody)) {
      lastPublishingTimeStamp = millis();
    }
  }
  
  mqttActuatorInterface.loop(); 
  delay(500);
}
