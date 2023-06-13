package com.iotassistant.models;

import java.io.IOException;

public class CameraInterfaceException extends IOException{

	private static final long serialVersionUID = 229119584259611285L;

	private String errorMessage;

	public CameraInterfaceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	

}