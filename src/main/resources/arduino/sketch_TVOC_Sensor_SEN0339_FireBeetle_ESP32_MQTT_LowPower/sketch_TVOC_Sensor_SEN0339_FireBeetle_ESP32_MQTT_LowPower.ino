
#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include <DFRobot_CCS811.h>

#define US_TO_SEC_FACTOR 1000000  /* Conversion factor for micro seconds to seconds */
#define MS_TO_SEC_FACTOR 1000  /* Conversion factor for ms seconds to seconds */ 

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Calidad del aire";
const uint32_t max_time_sec_between_publishing = 15; // At maximum 15 in order to keep the sensor measures stables (15 here means sensor will slept during 15 sec)
const unsigned long ntpSecUpdateInterval = 600;
const unsigned long sen0339_Baseline_sec_update_interval = 3600;


CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200);
DFRobot_CCS811 CCS811;

RTC_DATA_ATTR unsigned int timeSecSinceLastNTPUpdate = 0;
RTC_DATA_ATTR unsigned int timeSecSinceLastBaselineUpdate = 0;
RTC_DATA_ATTR unsigned long epochTime;

const unsigned long deep_sleep_time_us =  max_time_sec_between_publishing * US_TO_SEC_FACTOR; // we want to wake up to publish measure at least each max_time_sec_between_publishing value
DateTime currentDate;
unsigned long previousMillis = 0;
uint16_t sen0339_Baseline = 0xE3BD;


bool publishTVOCCO2SensorMeasure(float tvoc, float co2, DateTime date) {
  customSerial.println("Publish Air quality sensor measure");
  Measure measure(date);
  measure.addAnalogValue(ANALOG_AIR_TVOC_PPB, tvoc);
  measure.addAnalogValue(ANALOG_AIR_CO2_PPM, co2);
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
  unsigned long timeMSecAwaken = millis();
  unsigned long timeSecToAdd = (timeMSecAwaken - previousMillis) / MS_TO_SEC_FACTOR;
  previousMillis = timeMSecAwaken;
  if (addTimeAsleep) {
    timeSecToAdd += deep_sleep_time_us / US_TO_SEC_FACTOR;
  }
  timeSecSinceLastNTPUpdate += timeSecToAdd;
  epochTime += timeSecToAdd;
  timeSecSinceLastBaselineUpdate += timeSecToAdd;
}


void initCCS811(){
  unsigned long currentTime = millis();
  while(CCS811.begin() != 0 && millis() < currentTime + 2000){
  }
  currentTime = millis();
  while(!CCS811.checkDataReady() &&  millis() < currentTime + 2000){
  }
  if (timeSecSinceLastBaselineUpdate > sen0339_Baseline_sec_update_interval) {
    sen0339_Baseline = CCS811.readBaseLine();
    timeSecSinceLastBaselineUpdate = 0;
  }
  customSerial.print("Baseline: ");  
  customSerial.println(sen0339_Baseline); 
  CCS811.writeBaseLine(sen0339_Baseline);
}

void setup(){
 
  if (wakeUpCausedByTimer()) {
   updateRTCTimes(true); //Clocks dont work during deep sleep so we add manually seconds here to our RTC memory variables that carry out the time;
  }

  initCCS811();
  
  mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
  if (wiFiSTA.connect() && mqttSensorInterface.connect() && CCS811.checkDataReady()) {
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
    publishTVOCCO2SensorMeasure(CCS811.getTVOCPPB(), CCS811.getCO2PPM(), currentDate) ;
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
