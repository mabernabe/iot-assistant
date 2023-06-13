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
const String actuator_name = "boton led";
const uint32_t max_time_msec_between_publishing = 60000; 
const int sensorPin = D2;
boolean ledState = true;
unsigned long lastPublishingTimeStamp = 0;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttActuatorInterface mqttActuatorInterface(*client, actuator_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);


boolean publishLedActuatorValue(boolean ledState) {
  customSerial.println("Publish Led actuator value");
  timeClient.update();
  DateTime currentDate;
  currentDate.setEpochTime(timeClient.getEpochTime());
  Value value(currentDate);
  value.addDigitalValue(DIGITAL_LED, ledState);
  return mqttActuatorInterface.publishValue(value);
}

void setLed(boolean ledState) {
  pinMode(sensorPin, OUTPUT);
  if (ledState) {
    digitalWrite(sensorPin, HIGH); // Turn on
  }
  else {
    digitalWrite(sensorPin, LOW);
  }
}

void processSetStateMessage(byte* payload) {
  customSerial.println("Processing set state message");
  Value newValue;
  boolean error = mqttActuatorInterface.processSetStateMessage(payload, newValue);
  if (!error) {
    String valueString = newValue.getValue(DIGITAL_LED);
    error = Value::getDigitalValueFromString(valueString, &ledState);
    if (!error) {
      setLed(ledState);
      publishLedActuatorValue(ledState);
    }
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
 setLed(ledState);
}

    
void loop() {
  if (!mqttActuatorInterface.connected()) {
    mqttActuatorInterface.connectLoop();
  }

  if (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing || lastPublishingTimeStamp == 0) { 
    if (publishLedActuatorValue(ledState)) {
      lastPublishingTimeStamp = millis();
    }
  }
  
  mqttActuatorInterface.loop(); 
  delay(500);
}
