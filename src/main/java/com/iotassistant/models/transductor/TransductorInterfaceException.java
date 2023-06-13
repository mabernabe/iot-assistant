package com.iotassistant.models.transductor;

import java.io.IOException;

public class TransductorInterfaceException extends IOException{

	private static final long serialVersionUID = 229119584259611285L;

	private String errorMessage;

	public TransductorInterfaceException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	

}
