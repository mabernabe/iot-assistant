#include <M5AtomS3Gps.h>
#include <ESP32WifiSTA.h>
#include <CustomSerial.h>
#include <MqttGpsPublishInterface.h>
#include <TinyGPS++.h>

// WiFi Settings.
const char* wifi_ssid = "WIFI_MB";
const char* wifi_password = "directordelaescuela";

// MQTT Settings
const char* mqtt_server = "7f6a0b40dedf4d84b0c1f6250c512408.s1.eu.hivemq.cloud";
const uint16_t mqtt_server_port = 8883;
const String topic = "Coche";
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
MqttGpsPublishInterface mqttGpsPublishInterface(*wiFiSTA.getNewSecureClient(ssl_ca_cert), topic, customSerial);
M5AtomS3GpsSettings m5AtomS3GpsSettings(DEEP_SLEEP_ACTIVE, PUBLISH_POSITION_1_MINUTE_INTERVAL);
M5AtomS3Gps m5AtomS3Gps(m5AtomS3GpsSettings, mqttGpsPublishInterface, wiFiSTA.getNewUDPClient());

HardwareSerial GPSRaw(2);
TinyGPSPlus gps;

int cnt=0;

void setup() {
  m5AtomS3Gps.begin();
  Serial.println("Before loop");
  wiFiSTA.connectLoop();
  Serial.println("After loop");
  mqttGpsPublishInterface.setBroker(mqtt_server, mqtt_server_port);
  Serial.println("After set broker");
  mqttGpsPublishInterface.setAuth(username, password);
  m5AtomS3Gps.setLatitudeCallback(latitudeCallback);
  m5AtomS3Gps.setLongitudeCallback(longitudeCallback);
  m5AtomS3Gps.setIsPositionReadyCallback(isPositionReadyCallback);
  Serial.println("After set callbacks");
  GPSRaw.begin(9600, SERIAL_8N1, 1, 2);
  Serial.println("After set GPS");
}

bool isPositionReadyCallback() {
  return gps.location.isValid();
}

String latitudeCallback() {
  return gps.location.isValid() ? String(gps.location.lat()) : "";
}

String longitudeCallback() {
  return gps.location.isValid() ? String(gps.location.lng()) : "";
}

void loop() {
  Serial.println("starting loop");
  while(GPSRaw.available()>0 && !gps.encode(GPSRaw.read()));
  smartDelay(1000);
  Serial.println("After delay");
  m5AtomS3Gps.loop();
  Serial.println("After m5atom loop");
}

static void smartDelay(unsigned long ms) {
    unsigned long start = millis();
    do {
        while (GPSRaw.available()) gps.encode(GPSRaw.read());
    } while (millis() - start < ms);
}
