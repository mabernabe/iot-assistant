package com.iotassistant.controllers.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.Device;

public class DeviceDTO {
	
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
    @Size(max = 50)
	private boolean active;
	
	@JsonProperty(required = true)
    @NotNull
	private String watchdogInterval;
	
	private boolean watchdogEnabled;
	
	public DeviceDTO(Device device) {
		super();
		this.name = device.getName();
		this.description = device.getDescription();
		this.active = device.isActive();
		this.watchdogInterval = device.getWatchdogInterval().toString();
		this.watchdogEnabled = device.isWatchdogEnabled();
	}

	public DeviceDTO() {
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	

	public boolean isActive() {
		return active;
	}

	public String getWatchdogInterval() {
		return watchdogInterval;
	}

	public boolean isWatchdogEnabled() {
		return watchdogEnabled;
	}

}
