package com.iotassistant.models.transductor;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.iotassistant.models.Device;
import com.iotassistant.models.DeviceVisitor;

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
	
	public abstract List<Property>getProperties();

	public abstract List<TransductorValue> getTransductorValues() throws TransductorInterfaceException;

	public abstract void setUpInterface() throws TransductorInterfaceException;

	public abstract void setDownInterface() throws TransductorInterfaceException;
	
	@Override
	public void accept(DeviceVisitor deviceVisitor) {
		deviceVisitor.visit(this);
	}
	
}
