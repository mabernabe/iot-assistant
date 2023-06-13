package com.iotassistant.models.transductor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

@Entity
@DiscriminatorValue("Actuator")
@Table(name="actuator")
public class Actuator extends Transductor{
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	ActuatorInterface actuatorInterface;
	
	
	public Actuator() {
		super();
	}


	public Actuator(String name, String description, ActuatorInterface actuatorInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.actuatorInterface = actuatorInterface;
	}

	
	public void setValue(PropertyActuatedEnum propertyActuated, String value) throws TransductorInterfaceException {
		actuatorInterface.setValue(propertyActuated, value);
	}
	
	public List<ActuatorValue> getValues() throws TransductorInterfaceException {
		return actuatorInterface.getValues();
	}
	
	public ActuatorInterface getInterface() {
		return actuatorInterface;
	}


	public void setInterface(ActuatorInterface actuatorInterface) {
		this.actuatorInterface = actuatorInterface;
		
	}


	@Override
	public List<Property> getProperties() {
		return actuatorInterface.getProperties();
	}

	@Override
	public void setUpInterface() throws TransductorInterfaceException {
		actuatorInterface.setUp();		
	}
	
	@Override
	public void setDownInterface() throws TransductorInterfaceException {
		actuatorInterface.setDown();		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TransductorValue> getTransductorValues() throws TransductorInterfaceException {
		return (List<TransductorValue>) (List<? extends TransductorValue>) this.getValues();
	}


}
