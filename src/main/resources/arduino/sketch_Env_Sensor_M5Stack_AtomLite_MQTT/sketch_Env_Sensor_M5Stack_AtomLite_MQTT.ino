#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5_ENV.h"

// WiFi Settings.
const char* wifi_ssid = "NuCom_7077F0";
const char* wifi_password = "eBbLySD1TabK";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de ambiente";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

SHT3X sht30;
QMP6988 qmp6988;

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 Wire.begin(26, 32);  
 qmp6988.init();
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_TEMPERATURE_CENTIGRADES, getTemperatureMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_HUMIDITY_PERCENTAGE, getHumidityMeasureCallback));
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings(ANALOG_AIR_PRESSURE_PA, getPressureMeasureCallback));
}


CallbackAnalogMeasureSetting getCallbackMeasureSettings(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = analogPropertyMeasured;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

String getTemperatureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  if (sht30.get() == 0) {
    return String(sht30.cTemp); 
  }
  return "0";
}

String getHumidityMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  if (sht30.get() == 0) {
    return String(sht30.humidity); 
  }
  return "0";
}

String getPressureMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return String(qmp6988.calcPressure());
}

bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}


void loop() {
  qmp6988.calcPressure();
  m5AtomSensor.loop();
}
