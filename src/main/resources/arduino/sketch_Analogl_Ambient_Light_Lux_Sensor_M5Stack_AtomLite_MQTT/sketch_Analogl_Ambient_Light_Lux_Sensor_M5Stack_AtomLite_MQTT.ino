#include <M5AtomSensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include <M5_DLight.h>

// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "broker.hivemq.com";
const uint16_t mqtt_server_port = 1883;
const String topic = "Sensor de movimiento";

CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewClient(), topic, customSerial);
M5AtomSensorSettings m5AtomSensorSettings(PUBLISH_MEASURE_1_MINUTE_INTERVAL, LED_ACTIVE);
M5AtomSensor m5AtomSensor(m5AtomSensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());
M5_DLight sensor;

void setup(){
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 sensor.begin(&Wire1);
 sensor.setMode(CONTINUOUSLY_H_RESOLUTION_MODE);
 m5AtomSensor.addCallbackMeasureSettings(getCallbackMeasureSettings());
}

String getMeasureCallback(AnalogPropertyMeasured propertyMeasured) {
  return (String)sensor.getLUX();
}

bool shouldPublishChangeCallback(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  static const int lux_change_publish_threshold = 300; 
  return abs(newValue.toInt() - lastValue.toInt()) > lux_change_publish_threshold;
}

CallbackAnalogMeasureSetting getCallbackMeasureSettings() {
 CallbackAnalogMeasureSetting callbackMeasureSettings;
 callbackMeasureSettings.propertyMeasured = ANALOG_AMBIENT_LIGHT_LUX;
 callbackMeasureSettings.getMeasureCallback = getMeasureCallback;
 callbackMeasureSettings.shouldPublishChangeCallback = shouldPublishChangeCallback;
 return callbackMeasureSettings;
}

void loop() {
  m5AtomSensor.loop();
}
