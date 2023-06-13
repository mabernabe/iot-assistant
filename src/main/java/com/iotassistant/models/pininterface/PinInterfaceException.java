package com.iotassistant.models.pininterface;

import java.io.IOException;

public class PinInterfaceException extends IOException{


	private static final long serialVersionUID = -2190919091908978899L;
	private String errorMessage;

	public PinInterfaceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	

}
