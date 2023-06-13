#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include <DFRobot_PAJ7620U2.h>



// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de gestos";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

DFRobot_PAJ7620U2 sensor;


void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 while (sensor.begin(&Wire, 26, 32) != 0) {
   Serial.println("initial PAJ7620U2 failure!");
   delay(500);
 }
 sensor.setGestureHighRate(true);
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_GESTURE_NA, getGestureMeasureCallback));
}


CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getGestureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  DFRobot_PAJ7620U2::eGesture_t gesture = sensor.getGesture();
  gesture != sensor.eGestureNone;
  switch (gesture) {
   case DFRobot_PAJ7620U2::eGestureUp:
     return "1";
   case DFRobot_PAJ7620U2::eGestureDown: 
     return "2";
   case DFRobot_PAJ7620U2::eGestureLeft:
      return "3";
   case DFRobot_PAJ7620U2::eGestureRight:
      return "4";
   case DFRobot_PAJ7620U2::eGestureClockwise:
      return "5";
    case DFRobot_PAJ7620U2::eGestureAntiClockwise:
      return "6";
    case DFRobot_PAJ7620U2::eGestureForward:
      return "7";
    case DFRobot_PAJ7620U2::eGestureBackward:
      return "8";
    default: 
      return "0";       
  }
}


bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return !newValue.equals(lastValue) && !newValue.equals("0");
}


void loop() {
   m5AtomSensor.loop();
}
