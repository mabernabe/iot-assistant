/*
  Position.h 
*/


#ifndef Position_h
#define Position_h

#include <Arduino.h>
#include <DateTime.h>

namespace position {

class Position
{
	
  public:
    Position(DateTime date);
	void setLatitude(String latitude);
	void setLongitude(String latitude);
	String getJSONString(void);
	String getLatitude();
	String getLongitude();

  private:
    String latitude;
	String longitude;
    DateTime date;
};

}

#endif
