package com.iotassistant.models.transductor;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.iotassistant.models.TransductorVisitor;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
@DiscriminatorValue("Sensor")
@Table(name="sensor")
public class Sensor extends Transductor {
	
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
	private SensorValues values;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	protected SensorInterface sensorInterface;
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@Enumerated(EnumType.STRING)
	private List<PropertyMeasuredEnum> propertiesMeasured;

	public Sensor() {
		super();
	}

	public Sensor(String name, String description, List<PropertyMeasuredEnum> properties, SensorInterface sensorInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.sensorInterface = sensorInterface;
		this.propertiesMeasured = properties;
		this.values = null;
	}

	public SensorValues getValues() {
		return values;
	}


	public SensorInterface getInterface() {
		return this.sensorInterface;
	}

	public void setInterface(SensorInterface sensorInterface) {
		this.sensorInterface = sensorInterface;
		
	}
	
	public void setValues(SensorValues values) {
		this.values = values;
		this.setActive(true);
	}

	public List<PropertyMeasuredEnum> getPropertiesMeasured() {
		return propertiesMeasured;
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
	





}
