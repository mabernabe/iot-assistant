package com.iotassistant.models.devices;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("Gps")
@Table(name="gps")
public class Gps extends Device{

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private GpsInterface gpsInterface;
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
	private GpsPosition position;

	public Gps() {
		super();
	}

	public Gps(String name, String description, GpsInterface gpsInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.gpsInterface = gpsInterface;
	}

	public void setInterface(GpsInterface gpsInterface) {
		this.gpsInterface = gpsInterface;
		
	}

	@Override
	public void accept(DeviceVisitor deviceVisitor) {
		deviceVisitor.visit(this);
		
	}

	@Override
	public GpsInterface getInterface() {
		return this.gpsInterface;
	}
	

	public String getLastValueDate() {
		return this.position.getDate();
	}
	

	public GpsPosition getPosition() {
		return position;
	}

	public void setPosition(GpsPosition position) {
		this.position = position;
		
	}

}
