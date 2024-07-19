package com.iotassistant.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {

	public <T> T parseJsonBodyAs(Class<T> dataClass, String body) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
		return objectMapper.readValue(body, dataClass);
	}
	
	
}
