#include <CustomSerial.h>
#include <ESP8266WifiSTA.h>
#include <MqttSensorInterface.h>
#include <CustomBME680.h>
#include <Measure.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "192.168.1.138";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor de ambiente";
const uint32_t max_time_msec_between_publishing = 60000;

CustomSerial customSerial(115200, true);;
ESP8266WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);
CustomBME680* bme680;

unsigned long lastPublishingTimeStamp = 0;

bool publishBME680SensorMeasure() {
  customSerial.println("Publish bme680 measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addValue(ANALOG_TEMPERATURE_CENTIGRADES, String(bme680->getTemperature()));
  measure.addValue(ANALOG_HUMIDITY_PERCENTAGE, String(bme680->getHumidity()));
  measure.addValue(ANALOG_AIR_PRESSURE_PA, String((int)bme680->getPressure()));
  measure.addValue(ANALOG_AIR_QUALITY_IAQ, String(bme680->getIAQ()));
  return mqttSensorInterface.publishMeasure(measure);
}


void setup() {
  wiFiSTA.connectLoop();
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  bme680 = new CustomBME680(BME680_I2C_ADDR_SECONDARY);
  timeClient.begin(); 
}


void loop() {
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.reconnectLoop();
  }
  if (bme680->acquireData() && (millis() - lastPublishingTimeStamp >= max_time_msec_between_publishing)) {
    if (publishBME680SensorMeasure()) {
      lastPublishingTimeStamp = millis();
    }
  }
  mqttSensorInterface.loop(); 
}
