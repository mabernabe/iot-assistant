
#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor Button";
const uint32_t max_time_sec_between_publishing = 60; 
const gpio_num_t sensorGPIO = GPIO_NUM_25;

#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);

RTC_DATA_ATTR bool buttonValueAtWokenUp = false; //Hold the value that triggers the esp32 to wake up


bool publishButtomSensorMeasure(bool value) {
  customSerial.println("Publish Button sensor measure");
  timeClient.update();
  DateTime currentDate;
  currentDate.setEpochTime(timeClient.getEpochTime());
  Measure measure(currentDate);
  measure.addDigitalValue(DIGITAL_BUTTON, value);
  return mqttSensorInterface.publishMeasure(measure);
}


void setupDeepSleepWakeup(bool sensorValue, gpio_num_t sensorGPIOPin){
  buttonValueAtWokenUp = !sensorValue;
  esp_sleep_enable_ext0_wakeup(sensorGPIOPin, buttonValueAtWokenUp); // If sensor value is HIGH, we want to be waken up when value goes to LOW and viceversa
  esp_sleep_enable_timer_wakeup(max_time_sec_between_publishing * US_TO_SEC_FACTOR); //Also we want to wake up to publish measure at least each max_time_sec_between_publishing value
}


bool getButtonSensorGoodValue(gpio_num_t sensorGPIOPin){
  bool isPushed = digitalRead(sensorGPIOPin) == HIGH;
  /* If we awake cause the sensor, we dont wont to read the current value (maybe it has changed during the wake up), we 
   *  want the value that triggered the esp32 wake up
   */
  if (esp_sleep_get_wakeup_cause() ==  ESP_SLEEP_WAKEUP_EXT0) {
    isPushed = buttonValueAtWokenUp;
  }
  return isPushed;
}


void setup(){
  pinMode(sensorGPIO, INPUT);
  bool isPushed = getButtonSensorGoodValue(sensorGPIO);
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  if (wiFiSTA.connect() && mqttSensorInterface.connect()) {
    publishButtomSensorMeasure(isPushed);
    mqttSensorInterface.loop();
    delay(500); // Give time MQTT message to be published before going to sleep
  }
  setupDeepSleepWakeup(isPushed, sensorGPIO);
  Serial.println("Going to sleep now");    
  esp_deep_sleep_start();

}

void loop(){
  //This is not going to be called
}
