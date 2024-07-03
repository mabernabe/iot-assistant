package com.iotassistant.models.transductor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iotassistant.models.TransductorVisitor;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

@Entity
@DiscriminatorValue("Actuator")
@Table(name="actuator")
public class Actuator extends Transductor{
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
	private ActuatorValues values;
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	ActuatorInterface actuatorInterface;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private List<PropertyActuatedEnum> propertiesActuated;
	
	
	public Actuator() {
		super();
	}


	public Actuator(String name, String description, ActuatorInterface actuatorInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.actuatorInterface = actuatorInterface;
	}

	
	public void setValue(PropertyActuatedEnum propertyActuated, String value) throws TransductorInterfaceException {
		this.values.set(propertyActuated, value);
	}
	
	public ActuatorInterface getInterface() {
		return actuatorInterface;
	}


	public void setInterface(ActuatorInterface actuatorInterface) {
		this.actuatorInterface = actuatorInterface;
		
	}

	@Override
	public List<Property> getProperties() {
		List<Property> properties = new ArrayList<Property>();
		for (PropertyActuatedEnum propertyActuated : propertiesActuated){
			properties.add(propertyActuated);
		}
		return properties;
	}


	@Override
	public void accept(TransductorVisitor transductorVisitor) {
		transductorVisitor.visit(this);		
	}


	@Override
	public String getLastValueDate() {
		assert(this.isActive());
		return this.values.getDate();
	}


	public ActuatorValues getValues() {
		return this.values;	
	}


}
