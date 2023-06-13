#include <CustomSerial.h>
#include <ESP8266WifiSTA.h>
#include <MqttSensorInterface.h>
#include <CustomBME680.h>
#include <Measure.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "NuCom_7077F0";
const char* wifi_password = "eBbLySD1TabK";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String uv_sensor_topic = "Jardin sensor uv";

CustomSerial customSerial(115200, true);;
ESP8266WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* uvSensorClient = wiFiSTA.getNewClient();
MqttSensorInterface mqttUVSensorInterface(*uvSensorClient, uv_sensor_topic, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);



void publishUVSensorMeasure() {
  customSerial.println("Publish uv measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  int analogValue = analogRead(0);
  measure.addValue(ANALOG_UV_UVI, String(analogValue));
  mqttUVSensorInterface.publishMeasure(measure);
}


void setup() {
  wiFiSTA.connectLoop();
  mqttUVSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  timeClient.begin(); 
}



void uvSensorLoop() {
  if (!mqttUVSensorInterface.connected()) {
    mqttUVSensorInterface.reconnectLoop();
  }
  publishUVSensorMeasure();
  mqttUVSensorInterface.loop();
}


void loop() {
  uvSensorLoop();
  delay(10000);
  
}
