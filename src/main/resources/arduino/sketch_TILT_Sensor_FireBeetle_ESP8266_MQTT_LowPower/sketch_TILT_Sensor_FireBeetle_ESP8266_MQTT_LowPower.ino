
#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "192.168.1.138";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor TILT";
const uint32_t max_time_sec_between_publishing = 60; 
const gpio_num_t sensorGPIO = GPIO_NUM_25;

#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

RTC_DATA_ATTR bool tiltValueAtWokenUp = false; //Hold the value that triggers the esp32 to wake up


bool publishTiltSensorMeasure(bool value) {
  customSerial.println("Publish TILT sensor measure");
  timeClient.update();
  Measure measure(timeClient.getFullFormattedTime());
  measure.addDigitalValue(DIGITAL_TILT, value);
  return mqttSensorInterface.publishMeasure(measure);
}


void setupDeepSleepWakeup(bool sensorValue, gpio_num_t sensorGPIOPin){
  tiltValueAtWokenUp = !sensorValue;
  esp_sleep_enable_ext0_wakeup(sensorGPIOPin, tiltValueAtWokenUp); // If sensor value is HIGH, we want to be waken up when value goes to LOW and viceversa
  esp_sleep_enable_timer_wakeup(max_time_sec_between_publishing * US_TO_SEC_FACTOR); //Also we want to wake up to publish measure at least each max_time_sec_between_publishing value
}


bool getTiltSensorGoodValue(gpio_num_t sensorGPIOPin){
  bool isTilt = digitalRead(sensorGPIOPin) == HIGH;
  /* If we awake cause the sensor, we dont wont to read the current value (maybe it has changed during the wake up), we 
   *  want the tilt value that triggered the esp32 wake up
   */
  if (esp_sleep_get_wakeup_cause() ==  ESP_SLEEP_WAKEUP_EXT0) {
    isTilt = tiltValueAtWokenUp;
  }
  return isTilt;
}


void setup(){
  pinMode(sensorGPIO, INPUT);

  bool isTilt = getTiltSensorGoodValue(sensorGPIO);
  
  wiFiSTA.connectLoop();
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.reconnectLoop();
  }
  
  while (!publishTiltSensorMeasure(isTilt)) {
     mqttSensorInterface.loop();
  }
  mqttSensorInterface.loop();
  delay(500); // Give time MQTT message to be published before going to sleep
  
  setupDeepSleepWakeup(isTilt, sensorGPIO);
  Serial.println("Going to sleep now");    
  esp_deep_sleep_start();

}

void loop(){
  //This is not going to be called
}
