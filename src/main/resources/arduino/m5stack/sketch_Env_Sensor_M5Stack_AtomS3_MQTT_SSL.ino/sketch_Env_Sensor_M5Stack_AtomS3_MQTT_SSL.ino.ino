#include <M5AtomS3Sensor.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttSensorPublishInterface.h>
#include "M5UnitENV.h"

// WiFi Settings.
const char* wifi_ssid = "MiFibra-MB";
const char* wifi_password = "directoresdelaescuela";

// MQTT Settings
const char* mqtt_server = "7f6a0b40dedf4d84b0c1f6250c512408.s1.eu.hivemq.cloud";
const uint16_t mqtt_server_port = 8883;
const String topic = "Ambiente salita";
const String username = "miguel";
const String password = "Pozadelasal.10";

static const char ssl_ca_cert[] PROGMEM = R"EOF(
-----BEGIN CERTIFICATE-----
MIIFBTCCAu2gAwIBAgIQS6hSk/eaL6JzBkuoBI110DANBgkqhkiG9w0BAQsFADBP
MQswCQYDVQQGEwJVUzEpMCcGA1UEChMgSW50ZXJuZXQgU2VjdXJpdHkgUmVzZWFy
Y2ggR3JvdXAxFTATBgNVBAMTDElTUkcgUm9vdCBYMTAeFw0yNDAzMTMwMDAwMDBa
Fw0yNzAzMTIyMzU5NTlaMDMxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBF
bmNyeXB0MQwwCgYDVQQDEwNSMTAwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK
AoIBAQDPV+XmxFQS7bRH/sknWHZGUCiMHT6I3wWd1bUYKb3dtVq/+vbOo76vACFL
YlpaPAEvxVgD9on/jhFD68G14BQHlo9vH9fnuoE5CXVlt8KvGFs3Jijno/QHK20a
/6tYvJWuQP/py1fEtVt/eA0YYbwX51TGu0mRzW4Y0YCF7qZlNrx06rxQTOr8IfM4
FpOUurDTazgGzRYSespSdcitdrLCnF2YRVxvYXvGLe48E1KGAdlX5jgc3421H5KR
mudKHMxFqHJV8LDmowfs/acbZp4/SItxhHFYyTr6717yW0QrPHTnj7JHwQdqzZq3
DZb3EoEmUVQK7GH29/Xi8orIlQ2NAgMBAAGjgfgwgfUwDgYDVR0PAQH/BAQDAgGG
MB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcDATASBgNVHRMBAf8ECDAGAQH/
AgEAMB0GA1UdDgQWBBS7vMNHpeS8qcbDpHIMEI2iNeHI6DAfBgNVHSMEGDAWgBR5
tFnme7bl5AFzgAiIyBpY9umbbjAyBggrBgEFBQcBAQQmMCQwIgYIKwYBBQUHMAKG
Fmh0dHA6Ly94MS5pLmxlbmNyLm9yZy8wEwYDVR0gBAwwCjAIBgZngQwBAgEwJwYD
VR0fBCAwHjAcoBqgGIYWaHR0cDovL3gxLmMubGVuY3Iub3JnLzANBgkqhkiG9w0B
AQsFAAOCAgEAkrHnQTfreZ2B5s3iJeE6IOmQRJWjgVzPw139vaBw1bGWKCIL0vIo
zwzn1OZDjCQiHcFCktEJr59L9MhwTyAWsVrdAfYf+B9haxQnsHKNY67u4s5Lzzfd
u6PUzeetUK29v+PsPmI2cJkxp+iN3epi4hKu9ZzUPSwMqtCceb7qPVxEbpYxY1p9
1n5PJKBLBX9eb9LU6l8zSxPWV7bK3lG4XaMJgnT9x3ies7msFtpKK5bDtotij/l0
GaKeA97pb5uwD9KgWvaFXMIEt8jVTjLEvwRdvCn294GPDF08U8lAkIv7tghluaQh
1QnlE4SEN4LOECj8dsIGJXpGUk3aU3KkJz9icKy+aUgA+2cP21uh6NcDIS3XyfaZ
QjmDQ993ChII8SXWupQZVBiIpcWO4RqZk3lr7Bz5MUCwzDIA359e57SSq5CCkY0N
4B6Vulk7LktfwrdGNVI5BsC9qqxSwSKgRJeZ9wygIaehbHFHFhcBaMDKpiZlBHyz
rsnnlFXCb5s8HKn5LsUgGvB24L7sGNZP2CX7dhHov+YhD+jozLW2p9W4959Bz2Ei
RmqDtmiXLnzqTpXbI+suyCsohKRg6Un0RC47+cpiVwHiXZAW+cn8eiNIjqbVgXLx
KPpdzvvtTnOPlC7SQZSYmdunr3Bf9b77AiC/ZidstK36dRILKz7OA54=
-----END CERTIFICATE-----
)EOF";


CustomSerial customSerial(115200, true);
ESP32WifiSTA wiFiSTA(wifi_ssid,wifi_password, customSerial);
MqttSensorPublishInterface mqttSensorPublishInterface(*wiFiSTA.getNewSecureClient(ssl_ca_cert), topic, customSerial);
M5AtomS3SensorSettings m5AtomS3SensorSettings(DEEP_SLEEP_ACTIVE, PUBLISH_MEASURE_1_MINUTE_INTERVAL);
M5AtomS3Sensor m5AtomS3Sensor(m5AtomS3SensorSettings, mqttSensorPublishInterface, wiFiSTA.getNewUDPClient());

SHT3X sht3x;
BMP280 bmp;

void setup(){
 m5AtomS3Sensor.begin();
 sht3x.begin(&Wire, SHT3X_I2C_ADDR, 2, 1, 400000U);
 bmp.begin(&Wire, BMP280_I2C_ADDR, 2, 1, 400000U);
 bmp.setSampling(BMP280::MODE_NORMAL, BMP280::SAMPLING_X2, BMP280::SAMPLING_X16, BMP280::FILTER_X16, BMP280::STANDBY_MS_500); 
 wiFiSTA.connectLoop();
 mqttSensorPublishInterface.setBroker(mqtt_server, mqtt_server_port);
 mqttSensorPublishInterface.setAuth(username, password);
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_TEMPERATURE_CENTIGRADES, getTemperature));
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_HUMIDITY_PERCENTAGE, getHumidity));
 m5AtomS3Sensor.addMeasureSetting(buildMeasureSetting(ANALOG_AIR_PRESSURE_PA, getPressure));
 m5AtomS3Sensor.setIsMeasureReadyCallback(isMeasureReady);
}


AnalogMeasureSetting buildMeasureSetting(AnalogPropertyMeasured analogPropertyMeasured, String (*getMeasureCallback)(AnalogPropertyMeasured propertyMeasured)) {
 AnalogMeasureSetting analogMeasureSetting;
 analogMeasureSetting.propertyMeasured = analogPropertyMeasured;
 analogMeasureSetting.getMeasure = getMeasureCallback;
 analogMeasureSetting.shouldPublishChange = shouldPublishChange;
 return analogMeasureSetting;
}

String getTemperature(AnalogPropertyMeasured propertyMeasured) {
  return String(sht3x.cTemp);
}

String getHumidity(AnalogPropertyMeasured propertyMeasured) {
  return String(sht3x.humidity);
}

String getPressure(AnalogPropertyMeasured propertyMeasured) {
  return String(bmp.pressure);
}

bool shouldPublishChange(AnalogPropertyMeasured propertyMeasured, String lastValue, String newValue) {
  return false;
}

bool isMeasureReady() {
  return sht3x.update() && bmp.update();
}


void loop() {
  for(int i = 0; i < 100 && !isMeasureReady(); i++) {
    delay(100);
  }
  m5AtomS3Sensor.loop();
}
