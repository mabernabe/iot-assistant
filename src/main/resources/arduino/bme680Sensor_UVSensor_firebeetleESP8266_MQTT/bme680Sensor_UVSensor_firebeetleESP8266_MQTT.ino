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
const String bme68_sensor_topic = "Jardin sensor ambiente";
const String uv_sensor_topic = "Jardin sensor uv";

CustomSerial customSerial(115200, true);;
ESP8266WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* bme680SensorClient = wiFiSTA.getNewClient();
MqttSensorInterface mqttBME680SensorInterface(*bme680SensorClient, bme68_sensor_topic, customSerial);
Client* uvSensorClient = wiFiSTA.getNewClient();
MqttSensorInterface mqttUVSensorInterface(*uvSensorClient, uv_sensor_topic, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);
CustomBME680* bme680;


void publishBME680SensorMeasure() {
  customSerial.println("Publish bme680 measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addValue(ANALOG_TEMPERATURE_CENTIGRADES, String(bme680->getTemperature()));
  measure.addValue(ANALOG_HUMIDITY_PERCENTAGE, String(bme680->getHumidity()));
  measure.addValue(ANALOG_AIR_PRESSURE_PA, String((int)bme680->getPressure()));
  measure.addValue(ANALOG_AIR_QUALITY_IAQ, String(bme680->getIAQ()));
  mqttBME680SensorInterface.publishMeasure(measure);
}


void publishUVSensorMeasure() {
  customSerial.println("Publish uv measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  int sensorValue;
  int analogValue = analogRead(0);
  if (analogValue<20)
  {
    sensorValue = 0;
  }
  else
  {
    sensorValue = 0.05*analogValue-1;
  }
  measure.addValue(ANALOG_UV_UVI, String(sensorValue));
  mqttUVSensorInterface.publishMeasure(measure);
}


void setup() {
  wiFiSTA.connectLoop();
  mqttBME680SensorInterface.setBroker(mqtt_server, mqtt_server_port);
  mqttUVSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  bme680 = new CustomBME680(BME680_I2C_ADDR_SECONDARY);
  timeClient.begin(); 
}

void bme680SensorLoop() {
  if (!mqttBME680SensorInterface.connected()) {
    mqttBME680SensorInterface.reconnectLoop();
  }
  if (bme680->acquireData()) {
    publishBME680SensorMeasure();
  }
  mqttBME680SensorInterface.loop();
}

void uvSensorLoop() {
  if (!mqttUVSensorInterface.connected()) {
    mqttUVSensorInterface.reconnectLoop();
  }
  publishUVSensorMeasure();
  mqttUVSensorInterface.loop();
}


void loop() {
  bme680SensorLoop();
  uvSensorLoop();
  delay(20000);
  
}
