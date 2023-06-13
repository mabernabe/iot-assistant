package com.iotassistant.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.models.PlatformInterfacesFactory;
import com.iotassistant.models.exceptions.SystemCantShutdownException;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.transductor.TransductorInterfaceException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	private static int TRANSDUCTOR_INTERFACE_ERROR_CODE = 0xAA;
	
	private static int PIN_INTERFACE_ERROR_CODE = 0xAB;
	
	private static int CAMERA_INTERFACE_ERROR_CODE = 0xAC;
	
	@ExceptionHandler({CameraInterfaceException.class})
    public ResponseEntity<Object> handleCameraInterfaceException(
    		CameraInterfaceException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exception.getErrorMessage());
        body.put("errorCode", CAMERA_INTERFACE_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
			
	@ExceptionHandler({TransductorInterfaceException.class})
    public ResponseEntity<Object> handleTransductorInterfaceException(
    		TransductorInterfaceException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exception.getErrorMessage());
        body.put("errorCode", TRANSDUCTOR_INTERFACE_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler({PinInterfaceException.class})
    public ResponseEntity<Object> handlePinInterfaceException(
    		PinInterfaceException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", exception.getErrorMessage());
        body.put("errorCode", PIN_INTERFACE_ERROR_CODE);
        String platformName = PlatformInterfacesFactory.getInstance().getPlatformName();
        platformName = (platformName == null)? "NA" : "platformName"; 
        body.put("platform", platformName);
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
