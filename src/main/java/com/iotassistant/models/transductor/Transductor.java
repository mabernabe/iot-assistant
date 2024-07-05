package com.iotassistant.models.transductor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.iotassistant.models.Device;
import com.iotassistant.models.DeviceVisitor;
import com.iotassistant.models.TransductorVisitor;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="transductor_type")
@Table(name="transductor")
public abstract class Transductor extends Device{
	
	
	public Transductor() {
		super();
	}

	public Transductor(String name, String description, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
	}
	
	public abstract String getLastValueDate();

	@Override
	public void accept(DeviceVisitor deviceVisitor) {
		deviceVisitor.visit(this);
	}
	
	public abstract void accept(TransductorVisitor transductorVisitor);

	
	
	
}
