package com.iotassistant.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.exceptions.SystemCantShutdownException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
		
	private static int CAMERA_INTERFACE_ERROR_CODE = 0xAC;
	
	@ExceptionHandler({CameraInterfaceException.class})
    public ResponseEntity<Object> handleCameraInterfaceException(
    		CameraInterfaceException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exception.getErrorMessage());
        body.put("errorCode", CAMERA_INTERFACE_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
			
	
	@ExceptionHandler({SystemCantShutdownException.class})
    public ResponseEntity<Object> handleSystemCantShutdownException(
    		SystemCantShutdownException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "System can not power off because " + exception.getReason());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
