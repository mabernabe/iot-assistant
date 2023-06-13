/*
  CustomSerial.h - CustomSerial library - implementation
*/

#include "CustomSerial.h"


CustomSerial::CustomSerial()
{
  Serial.begin(115200);
  this->verbose = true;
}

CustomSerial::CustomSerial(int baudRate, bool verbose)
{
  Serial.begin(baudRate);
  this->verbose = verbose;
}

void CustomSerial::print(String string)
{
  if (this->verbose) {
	Serial.print(string);
  }
}

void CustomSerial::println(String string)
{
  this->print(string);
  this->println();
}

void CustomSerial::println()
{
  if (this->verbose) {
    Serial.println();
  }
}

void CustomSerial::print(IPAddress ipAddress)
{
  if (this->verbose) {
	Serial.print(ipAddress);
  }
}

void CustomSerial::println(IPAddress ipAddress)
{
  this->print(ipAddress);
  this->println();
}


void CustomSerial::print(float real)
{
  if (this->verbose) {
	Serial.print(real);
  }
}

void CustomSerial::println(float real)
{
  this->print(real);
  this->println();
}



