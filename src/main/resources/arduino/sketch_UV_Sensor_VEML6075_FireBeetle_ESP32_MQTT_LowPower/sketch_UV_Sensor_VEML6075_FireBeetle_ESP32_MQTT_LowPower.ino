
#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include <DFRobot_VEML6075.h>

#define VEML6075_ADDR   0x10
#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */
#define MS_TO_SEC_FACTOR 1000  /* Conversion factor for ms seconds to seconds */

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Sensor UV";
const uint32_t max_time_sec_between_publishing = 60;
const unsigned long ntpSecUpdateInterval = 600;


CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200);
DFRobot_VEML6075_IIC veml6075(&Wire, VEML6075_ADDR);

RTC_DATA_ATTR unsigned int timeSecSinceLastNTPUpdate = 0;
RTC_DATA_ATTR unsigned long epochTime;

const unsigned long deep_sleep_time_us =  max_time_sec_between_publishing * US_TO_SEC_FACTOR; // we want to wake up to publish measure at least each max_time_sec_between_publishing value
DateTime currentDate;


bool publishUVSensorMeasure(float uvi, float uva, float uvb, DateTime date) {
  customSerial.println("Publish UV sensor measure");
  Measure measure(date);
  measure.addAnalogValue(ANALOG_UV_UVI, uvi);
  measure.addAnalogValue(ANALOG_UV_UVA, uva);
  measure.addAnalogValue(ANALOG_UV_UVB, uvb);
  return mqttSensorInterface.publishMeasure(measure);
}

bool wakeUpCausedByTimer() {
  return esp_sleep_get_wakeup_cause() == ESP_SLEEP_WAKEUP_TIMER;
}

bool shouldUpdateEpochTimeWithNTP(){
  bool shouldUpdateDateWithNTP = true;
  if (wakeUpCausedByTimer()) {
    if (timeSecSinceLastNTPUpdate < ntpSecUpdateInterval) {
      shouldUpdateDateWithNTP = false;
    }
  }
  return shouldUpdateDateWithNTP;
}

bool updateEpochTimeWithNTP() {
  timeClient.begin();
  customSerial.println("Updating epoch time with NTP");
  bool updated = false;
  if (timeClient.forceUpdate()) {
    epochTime = timeClient.getEpochTime();
    updated = true;
  }
  return updated;
}

void updateRTCTimes(bool addTimeAsleep){
  unsigned long timeUSecAwake = millis();
  unsigned long timeUSecToAdd = timeUSecAwake;
  if (addTimeAsleep) {
    timeUSecToAdd += deep_sleep_time_us;
  }
  timeSecSinceLastNTPUpdate += timeUSecToAdd / US_TO_SEC_FACTOR;
  epochTime += timeUSecToAdd / US_TO_SEC_FACTOR;
}




void setup(){
  
  if (wakeUpCausedByTimer()) {
   updateRTCTimes(true); //Clocks dont work during deep sleep so we add manually seconds here to our RTC memory variables that carry out the time;
  }
    
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  if (wiFiSTA.connect() && mqttSensorInterface.connect() && veml6075.begin()) {
    bool ntpUpdated = false;     
    if (shouldUpdateEpochTimeWithNTP()) {
      if (updateEpochTimeWithNTP()) {
        timeSecSinceLastNTPUpdate = 0;
        ntpUpdated = true;
      }
    }
    if (!ntpUpdated) {
      updateRTCTimes(false); // We update again timers here cause maybe it took time to connect to the wifi and mqtt network
    }
    currentDate.setEpochTime(epochTime);
    float uva = veml6075.getUva();               
    float uvb = veml6075.getUvb();               
    float uvi = veml6075.getUvi(uva, uvb);
    publishUVSensorMeasure(uvi, uva, uvb, currentDate) ;
    mqttSensorInterface.loop();
    delay(500); // Give time MQTT message to be published before going to sleep   
  }
  
  esp_sleep_enable_timer_wakeup(deep_sleep_time_us); 
  updateRTCTimes(false); 
  customSerial.println("Going to sleep now");    
  esp_deep_sleep_start();

}

void loop(){
  //This is not going to be called
}
