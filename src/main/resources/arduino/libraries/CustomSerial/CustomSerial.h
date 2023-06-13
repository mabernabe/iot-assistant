/*
  CustomSerial.h 
*/


#ifndef CustomSerial_h
#define CustomSerial_h


#include <Arduino.h>
#include <IPAddress.h>



class CustomSerial
{
	
  private:
  bool verbose;

  public:
    CustomSerial();
    CustomSerial(int baudRate, bool verbose);
	void print(String string);
	void println(String string);
	void print(IPAddress string);
	void println(IPAddress string);
	void print(float real);
	void println(float real);
	void println();

};

#endif