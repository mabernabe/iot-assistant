package com.iotassistant.models.devices;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="transductor_type")
@Table(name="transductor")
public abstract class Transductor extends Device{
	
	
	public Transductor() {
		super();
	}

	Transductor(String name, String description, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
	}
	
	public abstract TransductorInterface getInterface();
	
	public abstract String getLastValueDate();
	
}
