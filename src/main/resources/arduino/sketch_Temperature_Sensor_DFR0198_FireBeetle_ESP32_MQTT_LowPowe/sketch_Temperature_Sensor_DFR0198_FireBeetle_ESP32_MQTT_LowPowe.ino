

#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "NuCom_7077F0";
const char* wifi_password = "eBbLySD1TabK";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Temperatura piscina";
const uint32_t max_time_sec_between_publishing = 60; 
const int sensorGPIO = D3;

#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200);
OneWire oneWire(sensorGPIO);
DallasTemperature sensors(&oneWire);



bool publishTemperatureSensorMeasure(float temperatureC) {
  customSerial.println("Publish temperature sensor measure");
  timeClient.update();
  DateTime currentDate;
  currentDate.setEpochTime(timeClient.getEpochTime());
  Measure measure(currentDate);
  measure.addAnalogValue(ANALOG_TEMPERATURE_CENTIGRADES, temperatureC);
  return mqttSensorInterface.publishMeasure(measure);
}


void setupDeepSleepWakeup(){
  esp_sleep_enable_timer_wakeup(max_time_sec_between_publishing * US_TO_SEC_FACTOR); //Also we want to wake up to publish measure at least each max_time_sec_between_publishing value
}




void setup(){  
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  if (wiFiSTA.connect() && mqttSensorInterface.connect()) {
    sensors.begin();
    sensors.requestTemperatures(); 
    float temperatureC = sensors.getTempCByIndex(0);
    publishTemperatureSensorMeasure(temperatureC);
    mqttSensorInterface.loop();
    delay(500); // Give time MQTT message to be published before going to sleep
  }
  
  setupDeepSleepWakeup();
  customSerial.println("Going to sleep now");    
  esp_deep_sleep_start();

}

void loop(){
  //This is not going to be called
}
