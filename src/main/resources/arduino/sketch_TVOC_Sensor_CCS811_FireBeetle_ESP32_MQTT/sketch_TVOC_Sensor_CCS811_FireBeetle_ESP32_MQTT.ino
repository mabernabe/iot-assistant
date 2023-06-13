#include <CustomSerial.h>
#include <ESP32WifiSTA.h>
#include <MqttSensorInterface.h>
#include <Measure.h>
#include <NTPClient.h>
#include <DFRobot_CCS811.h>

#define MS_TO_SEC_FACTOR 1000  /* Conversion factor for ms seconds to seconds */ 

// Update these with values suitable for your settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String sensor_name = "Calidad del aire";
const uint32_t max_time_sec_between_publishing = 60; 
const unsigned long sen0339_Baseline_sec_update_interval = 3600;

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
Client* client = wiFiSTA.getNewClient();
MqttSensorInterface mqttSensorInterface(*client, sensor_name, customSerial);
UDP* udpClient = wiFiSTA.getNewUDPClient();
NTPClient timeClient(*udpClient, "0.es.pool.ntp.org",7200,6000);
DFRobot_CCS811 CCS811;

uint16_t sen0339_Baseline = 0x04C2;
unsigned long lastSecBaselineUpdatedTimeStamp = 0;
unsigned long lastSecPublishingTimeStamp = 0;

bool publishTVOCCO2SensorMeasure(float tvoc, float co2) {
  customSerial.println("Publish Air quality sensor measure");
  DateTime currentDate;
  timeClient.update();
  currentDate.setEpochTime(timeClient.getEpochTime());
  Measure measure(currentDate);
  measure.addAnalogValue(ANALOG_AIR_TVOC_PPB, tvoc);
  measure.addAnalogValue(ANALOG_AIR_CO2_PPM, co2);
  return mqttSensorInterface.publishMeasure(measure);
}


void setupCCS811(){
  while(!CCS811.checkDataReady()){
    customSerial.println("data not ready!");
    delay(1000);
  }
  customSerial.print("Baseline: ");  
  customSerial.println(CCS811.readBaseLine()); 
}

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorInterface.setBroker(mqtt_server, mqtt_server_port);
 while(CCS811.begin() != 0){
    customSerial.println("failed to init chip, please check if the chip connection is fine");
    delay(1000);
 }
 CCS811.writeBaseLine(sen0339_Baseline);
}

    
void loop() {
  setupCCS811();  
  if (!mqttSensorInterface.connected()) {
    mqttSensorInterface.connectLoop();
  }
  /* We publish when max_time between publications is reached */
  unsigned long timeSecAwaken = millis() / MS_TO_SEC_FACTOR;
  if (timeSecAwaken - lastSecPublishingTimeStamp >= max_time_sec_between_publishing) { 
    if (publishTVOCCO2SensorMeasure(CCS811.getTVOCPPB(), CCS811.getCO2PPM())) {
      lastSecPublishingTimeStamp = timeSecAwaken;
    }
  } 
  mqttSensorInterface.loop(); 
  delay(1000);
}
