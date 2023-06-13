package com.iotassistant.controllers.dtos.transductor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public abstract class TransductorValueDTO {
	
	protected String property;
	
	protected String value;

	@JsonInclude(Include.NON_NULL)
	protected String unit;
	
	@JsonInclude(Include.NON_NULL)
	protected String date;
	
	protected String valueDescription;
	
	public String getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}

	public String getDate() {
		return date;
	}

	public String getValueDescription() {
		return valueDescription;
	}


}
