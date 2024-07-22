package com.iotassistant.services;

public class SystemCantShutdownException extends Exception {

	private static final long serialVersionUID = -7452428475759555501L;
	
	private String reason;

	public SystemCantShutdownException(String reason) {
		super();
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

}
