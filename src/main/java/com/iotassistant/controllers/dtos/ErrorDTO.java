package com.iotassistant.controllers.dtos;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorDTO {
	DEVICE_ALREADY_EXIST("Device with that name already exist", HttpStatus.UNPROCESSABLE_ENTITY),
	DEVICE_NOT_FOUND("Device not found", HttpStatus.NOT_FOUND),
	TRANSDUCTOR_HAS_NOT_PROPERTIES("Transductor has not properties", HttpStatus.UNPROCESSABLE_ENTITY),
	DEVICE_HAS_NOT_WATCHDOG("Device has not watchdog", HttpStatus.UNPROCESSABLE_ENTITY),
	CHART_ALREADY_EXIST("Chart with same properties already exist", HttpStatus.UNPROCESSABLE_ENTITY),
	CHART_NOT_FOUND("Chart not found", HttpStatus.NOT_FOUND),
	SENSOR_HAS_NOT_PROPERTY("Sensor has not %s property", HttpStatus.UNPROCESSABLE_ENTITY),
	ACTUATOR_HAS_NOT_PROPERTY("Actuator has not property", HttpStatus.UNPROCESSABLE_ENTITY),
	SENSOR_RULE_ALREADY_EXIST("Sensor rule with same properties already exist", HttpStatus.UNPROCESSABLE_ENTITY),
	SENSOR_RULE_NOT_FOUND("Sensor rule not found", HttpStatus.NOT_FOUND), 
	VALUE_IS_NOT_VALID("Value is invalid", HttpStatus.UNPROCESSABLE_ENTITY);
	
	private String message;
	
	private final HttpStatus httpStatus;

	private ErrorDTO(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}


	public void formatMessage(String string) {
		this.message = String.format(message, string);
		
	}
	
	
	

}
