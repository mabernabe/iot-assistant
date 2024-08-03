package com.iotassistant.models.devices;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="device_type")
@Table(name="device")
public abstract class Device {
	
	@Id
	private String name;

	private String description;
	
	private boolean active;
	
	@Enumerated(EnumType.STRING)
	private WatchdogInterval watchdogInterval;
	
	private boolean watchdogEnabled;
	
	public Device() {
		super();
	}
	
	public Device(String name, String description, WatchdogInterval watchdogInterval) {
		this();
		this.name = name;
		this.description = description;
		this.watchdogInterval = watchdogInterval;
		this.active = false;
		if (!watchdogInterval.equals(WatchdogInterval.NO_WATCHDOG)) {
			this.watchdogEnabled = true;
		}
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public abstract DeviceInterface getInterface();
	
	public WatchdogInterval getWatchdogInterval() {
		return watchdogInterval;
	}
	
	public boolean hasWatchdog() {
		return watchdogInterval != WatchdogInterval.NO_WATCHDOG;
	}
	
	public boolean isWatchdogEnabled() {
		return watchdogEnabled;
	}

	public void setWatchdogEnabled(boolean watchdogEnabled) {
		this.watchdogEnabled = watchdogEnabled;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public abstract void accept(DeviceVisitor deviceVisitor);
	
}
