package com.iotassistant.controllers.dtos.transductor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

class NewTransductorRequestDTO {
	
	@JsonProperty(required = true)
    @NotNull
    @Size(max = 30)
    private String name;
	
	@JsonProperty(required = true)
    @NotNull
    @Size(max = 50)
	private String description;
	
	@JsonProperty(required = true)
    @NotNull
	private String watchdogInterval;
	

	public String getName() {
		return name;
	}


	public void setName(String sensorName) {
		this.name = sensorName;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public void setWatchdogInterval(String watchdogInterval) {
		this.watchdogInterval = watchdogInterval;
	}


	public String getDescription() {
		return description;
	}


	public String getWatchdogInterval() {
		return watchdogInterval;
	}

	
	
	
	

}
