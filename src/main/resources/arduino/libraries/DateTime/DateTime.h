/*
  DateTime.h 
*/


#ifndef DateTime_h
#define DateTime_h

#include "Arduino.h"

enum  DateTimeFormat {YYYY_MM_DD_HH_MM_SS};



class DateTime
{
	
  private:
  unsigned long epochTime;

  public:
    DateTime();
    DateTime(unsigned long epochTime);
	String getFullFormattedDateTime(DateTimeFormat format);
	unsigned long getEpochTime();
	void setEpochTime(unsigned long epochTime);

};

#endif