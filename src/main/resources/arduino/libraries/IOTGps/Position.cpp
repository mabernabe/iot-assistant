/*
  Position.cpp
*/

#include "Position.h"

using namespace position;

Position::Position(DateTime date)
{
  this->date = date;
}


void Position::setLatitude(String latitude)
{
  this->latitude = latitude;
}

void Position::setLongitude(String longitude)
{
  this->longitude = longitude;
}


String Position::getLatitude()
{
  return this->latitude;
}

String Position::getLongitude()
{
  return this->longitude;
}

String Position::getJSONString(void)
{
  String json = String ("{ ");
  json += String("\"longitude\" : " + String("\"") + String(this->longitude) + String("\"") + String(", "));
  json += String("\"latitude\" : " + String("\"") + String(this->latitude) + String("\"") + String(", "));
  json += String(" \"date\" : ") + String("\"") + this->date.getFullFormattedDateTime(YYYY_MM_DD_HH_MM_SS) + String("\"");
  json+= String(" }");
  return json;
}

