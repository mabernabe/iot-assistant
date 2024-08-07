#include <M5AtomS3Gps.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttGpsPublishInterface.h>
#include <TinyGPS++.h>

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Coche";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttGpsPublishInterface mqttGpsPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
m5AtomS3GpsS3Settings m5AtomS3GpsSettings(DEEP_SLEEP_ACTIVE, PUBLISH_POSITION_1_MINUTE_INTERVAL);
m5AtomS3Gps m5AtomS3Gps(m5AtomS3GpsSettings, mqttGpsPublishInterface, wiFiSTA.getNewUDPClient());

HardwareSerial GPSRaw(2);
TinyGPSPlus gps;

int cnt=0;

void setup() {
  wiFiSTA.connectLoop();
  mqttGpsPublishInterface.setBroker(mqtt_server, mqtt_server_port);
  m5AtomS3Gps.setLatitudeCallback(latitudeCallback);
  m5AtomS3Gps.setLongitudeCallback(longitudeCallback);
  m5AtomS3Gps.setIsPositionReadyCallback(isPositionReadyCallback);
  GPSRaw.begin(9600, SERIAL_8N1, 32, 26);
}

bool isPositionReadyCallback() {
  return gps.location.isValid();
}

String latitudeCallback() {
  return gps.location.isValid() ? String(gps.location.lat()) : "";
}

String longitudeCallback() {
  return gps.location.isValid() ? String(gps.location.lng()) : "";
}

void loop() {
  while(GPSRaw.available()>0 && !gps.encode(GPSRaw.read()));
  smartDelay(1000);
  m5AtomS3Gps.loop();
}

static void smartDelay(unsigned long ms) {
    unsigned long start = millis();
    do {
        while (GPSRaw.available()) gps.encode(GPSRaw.read());
    } while (millis() - start < ms);
}
