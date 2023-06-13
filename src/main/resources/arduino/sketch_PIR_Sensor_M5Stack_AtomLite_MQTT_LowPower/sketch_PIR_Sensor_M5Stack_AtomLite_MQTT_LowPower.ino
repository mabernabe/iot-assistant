

#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include <Timezone.h> 
#include <M5Atom.h>

// Update these with values suitable for your settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor de movimiento";
const uint32_t max_time_sec_between_publishing = 60;
const gpio_num_t sensorGPIO = GPIO_NUM_32;

#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",0,6000);
TimeChangeRule SDT = {"SDT", Last, Sun, Mar, 2, 0};  //Spain Daylight Time UTC + 0 hours
TimeChangeRule SST = {"SST", Last, Sun, Oct, 3, 60};   //Spain Standard Time UTC + 1 hours
Timezone spainTZ(SDT,SST);

RTC_DATA_ATTR bool pirValueAtWokenUp = false; //Hold the value that triggers the esp32 to wake up


bool publishPIRSensorMeasure(bool isMotionDetected) {
  customSerial.println("Publish PIR sensor measure");
  timeClient.update();
  DateTime currentDate;
  long utcEpochTime = timeClient.getEpochTime(); 
  currentDate.setEpochTime(spainTZ.toLocal(utcEpochTime));
  Measure measure(currentDate);
  measure.addDigitalValue(DIGITAL_MOTION, isMotionDetected);
  return mqttSensorInterface.publishMeasure(measure);
}


void setupDeepSleepWakeup(bool sensorValue, gpio_num_t sensorGPIOPin){
  pirValueAtWokenUp = !sensorValue;
  esp_sleep_enable_ext0_wakeup(sensorGPIOPin, pirValueAtWokenUp); // If sensor value is HIGH, we want to be waken up when value goes to LOW and viceversa
  esp_sleep_enable_timer_wakeup(max_time_sec_between_publishing * US_TO_SEC_FACTOR); //Also we want to wake up to publish measure at least each max_time_sec_between_publishing value
}


bool getPIRSensorGoodValue(){
  bool isMotionDetected =  digitalRead(sensorGPIO);
  /* If we awake cause the sensor, we dont wont to read the current value (maybe it has changed during the wake up), we 
   *  want the tilt value that triggered the esp32 wake up
   */
  if (esp_sleep_get_wakeup_cause() ==  ESP_SLEEP_WAKEUP_EXT0) {
    isMotionDetected = pirValueAtWokenUp;
  }
  return isMotionDetected;
}


void setup(){
  wiFiSTA.connectLoop();
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  customSerial.println("Setting Atom PIN");
  M5.begin(true, false, false);
  pinMode(sensorGPIO, INPUT); 
  customSerial.println("Setting Atom PIN done");


  bool isMotionDetected = getPIRSensorGoodValue();
  
  
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.connectLoop();
  }
  
  while (!publishPIRSensorMeasure(isMotionDetected)) {
     mqttSensorInterface.loop();
  }
  mqttSensorInterface.loop();
  delay(500); // Give time MQTT message to be published before going to sleep
  
  setupDeepSleepWakeup(isMotionDetected, sensorGPIO);
  Serial.println("Going to sleep now");    
  esp_deep_sleep_start();

}

void loop(){
  //This is not going to be called
}
